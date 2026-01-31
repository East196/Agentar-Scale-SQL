package com.agentar.scalesql.data.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.agentar.scalesql.core.exception.ScaleSqlException;
import com.agentar.scalesql.core.model.RetrievalRequest;
import com.agentar.scalesql.core.model.RetrievalResponse;
import com.agentar.scalesql.data.repository.VectorStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于 Hologres 的向量存储实现
 * Hologres 是阿里云的实时数仓，支持向量检索、全文搜索和关系数据
 *
 * @author ScaleSQL Team
 */
@Slf4j
public class HologresVectorStore implements VectorStore {

    private final String tableName;
    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    /**
     * 构造函数
     *
     * @param dataSource 数据源
     * @param tableName  表名
     */
    public HologresVectorStore(DataSource dataSource, String tableName) {
        this.dataSource = dataSource;
        this.tableName = tableName;
    }

    @Override
    public void connect() {
        try {
            this.jdbcTemplate = new JdbcTemplate(dataSource);
            log.info("成功连接到 Hologres 向量存储，表名: {}", tableName);
        } catch (Exception e) {
            throw new ScaleSqlException("连接 Hologres 失败", e);
        }
    }

    @Override
    public void disconnect() {
        // Hologres 使用连接池，不需要显式断开
        log.info("断开 Hologres 连接");
    }

    @Override
    public List<String> addDocuments(List<Document> documents) {
        if (jdbcTemplate == null) {
            throw new ScaleSqlException("未连接到 Hologres，请先调用 connect()");
        }

        if (CollUtil.isEmpty(documents)) {
            return new ArrayList<>();
        }

        List<String> ids = new ArrayList<>();
        try {
            // 批量插入文档
            String sql = String.format(
                "INSERT INTO %s (id, content, metadata, embedding) VALUES (?, ?, ?::jsonb, ?)",
                tableName
            );

            for (int i = 0; i < documents.size(); i++) {
                Document doc = documents.get(i);
                String id = "doc_" + System.currentTimeMillis() + "_" + i;

                // 这里需要调用嵌入模型生成向量，暂时使用 null
                jdbcTemplate.update(sql, id, doc.getContent(),
                    convertMapToJson(doc.getMetadata()), null);

                ids.add(id);
            }

            log.info("成功添加 {} 个文档到 Hologres", documents.size());
        } catch (Exception e) {
            throw new ScaleSqlException("添加文档到 Hologres 失败", e);
        }

        return ids;
    }

    @Override
    public RetrievalResponse search(RetrievalRequest request) {
        if (jdbcTemplate == null) {
            throw new ScaleSqlException("未连接到 Hologres，请先调用 connect()");
        }

        try {
            List<RetrievalResponse.Document> documents = new ArrayList<>();

            // 根据检索模式选择不同的查询方式
            String sql;
            if ("vector".equals(request.getMode())) {
                // 向量检索：使用 Hologres 的向量相似度搜索
                sql = buildVectorSearchSql(request);
            } else if ("text".equals(request.getMode())) {
                // 全文检索：使用 Hologres 的全文搜索
                sql = buildTextSearchSql(request);
            } else {
                // 混合检索：结合向量和全文搜索
                sql = buildHybridSearchSql(request);
            }

            // 执行查询
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

            // 转换结果
            for (Map<String, Object> row : results) {
                RetrievalResponse.Document doc = RetrievalResponse.Document.builder()
                    .id((String) row.get("id"))
                    .content((String) row.get("content"))
                    .score(((Number) row.get("score")).doubleValue())
                    .metadata(parseJsonToMap((String) row.get("metadata")))
                    .build();

                // 过滤低于阈值的结果
                if (request.getThreshold() == null || doc.getScore() >= request.getThreshold()) {
                    documents.add(doc);
                }
            }

            return RetrievalResponse.builder()
                .documents(documents)
                .total(documents.size())
                .build();

        } catch (Exception e) {
            log.error("Hologres 检索失败", e);
            throw new ScaleSqlException("Hologres 检索失败", e);
        }
    }

    /**
     * 构建向量检索 SQL
     */
    private String buildVectorSearchSql(RetrievalRequest request) {
        // Hologres 向量检索语法
        // 使用 <-> 操作符计算向量距离
        return String.format(
            "SELECT id, content, metadata, " +
            "1 - (embedding <-> '[%s]'::vector) as score " +
            "FROM %s " +
            "ORDER BY embedding <-> '[%s]'::vector " +
            "LIMIT %d",
            request.getSearchQuery(), // 这里应该是向量，需要转换
            tableName,
            request.getSearchQuery(),
            request.getTopK()
        );
    }

    /**
     * 构建全文检索 SQL
     */
    private String buildTextSearchSql(RetrievalRequest request) {
        // Hologres 全文检索语法
        // 使用 to_tsvector 和 to_tsquery 进行全文搜索
        return String.format(
            "SELECT id, content, metadata, " +
            "ts_rank(to_tsvector('simple', content), to_tsquery('simple', '%s')) as score " +
            "FROM %s " +
            "WHERE to_tsvector('simple', content) @@ to_tsquery('simple', '%s') " +
            "ORDER BY score DESC " +
            "LIMIT %d",
            escapeSqlString(request.getSearchQuery()),
            tableName,
            escapeSqlString(request.getSearchQuery()),
            request.getTopK()
        );
    }

    /**
     * 构建混合检索 SQL
     */
    private String buildHybridSearchSql(RetrievalRequest request) {
        // 混合检索：结合向量相似度和全文搜索分数
        return String.format(
            "SELECT id, content, metadata, " +
            "(0.7 * (1 - (embedding <-> '[%s]'::vector)) + " +
            " 0.3 * ts_rank(to_tsvector('simple', content), to_tsquery('simple', '%s'))) as score " +
            "FROM %s " +
            "ORDER BY score DESC " +
            "LIMIT %d",
            request.getSearchQuery(),
            escapeSqlString(request.getSearchQuery()),
            tableName,
            request.getTopK()
        );
    }

    /**
     * 转换 Map 到 JSON 字符串
     */
    private String convertMapToJson(Map<String, Object> map) {
        if (map == null) {
            return "{}";
        }
        // 使用 Hutool 的 JSON 工具
        return cn.hutool.json.JSONUtil.toJsonStr(map);
    }

    /**
     * 解析 JSON 字符串到 Map
     */
    private Map<String, Object> parseJsonToMap(String json) {
        if (StrUtil.isBlank(json)) {
            return new java.util.HashMap<>();
        }
        return cn.hutool.json.JSONUtil.toBean(json, Map.class);
    }

    /**
     * 转义 SQL 字符串
     */
    private String escapeSqlString(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("'", "''");
    }
}
