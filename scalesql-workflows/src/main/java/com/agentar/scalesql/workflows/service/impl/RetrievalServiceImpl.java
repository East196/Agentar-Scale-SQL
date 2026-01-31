package com.agentar.scalesql.workflows.service.impl;

import cn.hutool.core.util.StrUtil;
import com.agentar.scalesql.core.model.RetrievalRequest;
import com.agentar.scalesql.core.model.RetrievalResponse;
import com.agentar.scalesql.data.repository.VectorStore;
import com.agentar.scalesql.workflows.service.RetrievalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 检索服务实现
 *
 * @author ScaleSQL Team
 */
@Slf4j
@Service
public class RetrievalServiceImpl implements RetrievalService {

    @Resource
    private VectorStore vectorStore;

    @Override
    public List<CellRetrievalResult> retrieveDatabaseCells(
        List<String> databaseLiterals,
        Double threshold,
        Integer topK
    ) {
        log.info("开始数据库单元检索，关键词数量: {}", databaseLiterals.size());

        Set<String> resultSet = new HashSet<>();
        List<CellRetrievalResult> results = new ArrayList<>();

        for (String literal : databaseLiterals) {
            // 跳过数字和空字符串
            if (StrUtil.isBlank(literal) || literal.matches("\\d+")) {
                continue;
            }

            try {
                // 构建检索请求
                RetrievalRequest request = RetrievalRequest.builder()
                    .searchQuery(literal)
                    .mode("text")
                    .threshold(threshold)
                    .topK(topK)
                    .build();

                // 执行检索
                RetrievalResponse response = vectorStore.search(request);

                // 处理结果
                if (response.getDocuments() != null) {
                    for (RetrievalResponse.Document doc : response.getDocuments()) {
                        Map<String, Object> metadata = doc.getMetadata();
                        String table = (String) metadata.get("table");
                        String column = (String) metadata.get("column");
                        String content = doc.getContent();

                        // 去重
                        String key = table + "_" + column + "_" + content;
                        if (!resultSet.contains(key)) {
                            resultSet.add(key);
                            results.add(new CellRetrievalResult(
                                table, column, content, doc.getScore()
                            ));
                        }
                    }
                }

            } catch (Exception e) {
                log.error("检索失败，关键词: {}", literal, e);
            }
        }

        log.info("数据库单元检索完成，结果数量: {}", results.size());
        return results;
    }

    @Override
    public List<SkeletonRetrievalResult> retrieveSkeleton(
        String questionSkeleton,
        Double threshold,
        Integer topK
    ) {
        log.info("开始骨架检索，问题骨架: {}", questionSkeleton);

        try {
            // 构建检索请求
            RetrievalRequest request = RetrievalRequest.builder()
                .searchQuery(questionSkeleton)
                .mode("text")
                .threshold(threshold)
                .topK(topK)
                .indexName("skeleton_index")
                .build();

            // 执行检索
            RetrievalResponse response = vectorStore.search(request);

            // 转换结果
            List<SkeletonRetrievalResult> results = new ArrayList<>();
            if (response.getDocuments() != null) {
                for (RetrievalResponse.Document doc : response.getDocuments()) {
                    Map<String, Object> metadata = doc.getMetadata();
                    String question = (String) metadata.get("question");
                    String evidence = (String) metadata.get("evidence");
                    String sql = (String) metadata.get("sql");

                    results.add(new SkeletonRetrievalResult(
                        question, evidence, sql, doc.getScore()
                    ));
                }
            }

            log.info("骨架检索完成，结果数量: {}", results.size());
            return results;

        } catch (Exception e) {
            log.error("骨架检索失败", e);
            return new ArrayList<>();
        }
    }
}
