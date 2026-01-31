package com.agentar.scalesql.api.service.impl;

import cn.hutool.core.util.StrUtil;
import com.agentar.scalesql.api.service.Text2SqlService;
import com.agentar.scalesql.data.service.DatabaseSchemaService;
import com.agentar.scalesql.llm.service.LlmService;
import com.agentar.scalesql.workflows.service.RetrievalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Text-to-SQL 服务实现
 *
 * @author ScaleSQL Team
 */
@Slf4j
@Service
public class Text2SqlServiceImpl implements Text2SqlService {

    @Resource
    private LlmService llmService;

    @Resource
    private DatabaseSchemaService databaseSchemaService;

    @Resource
    private RetrievalService retrievalService;

    @Override
    public String generateSql(String question, String databaseName, String evidence) {
        log.info("开始生成 SQL，数据库: {}, 问题: {}", databaseName, question);

        try {
            // 1. 获取数据库 Schema
            String schema = getSchema(databaseName);

            // 2. 构建 Prompt
            String prompt = buildPrompt(question, schema, evidence);

            // 3. 调用 LLM 生成 SQL
            String sql = llmService.generate(prompt, 0.2, 1000);

            // 4. 后处理：提取 SQL
            sql = extractSql(sql);

            log.info("SQL 生成成功: {}", sql);
            return sql;

        } catch (Exception e) {
            log.error("SQL 生成失败", e);
            throw new RuntimeException("SQL 生成失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取数据库 Schema
     */
    private String getSchema(String databaseName) {
        // TODO: 从数据库中获取 Schema
        return "-- Database Schema for " + databaseName + "\n" +
               "-- TODO: Load actual schema from database";
    }

    /**
     * 构建 Prompt
     */
    private String buildPrompt(String question, String schema, String evidence) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are an expert SQL developer. Generate a SQL query based on the following information:\n\n");

        // 添加 Schema
        prompt.append("Database Schema:\n");
        prompt.append(schema);
        prompt.append("\n\n");

        // 添加问题
        prompt.append("Question: ");
        prompt.append(question);
        prompt.append("\n\n");

        // 添加证据（如果有）
        if (StrUtil.isNotBlank(evidence)) {
            prompt.append("Evidence: ");
            prompt.append(evidence);
            prompt.append("\n\n");
        }

        prompt.append("Please generate a valid SQL query that answers the question. ");
        prompt.append("Return ONLY the SQL query without any explanation.\n\n");
        prompt.append("SQL:");

        return prompt.toString();
    }

    /**
     * 从 LLM 输出中提取 SQL
     */
    private String extractSql(String output) {
        if (StrUtil.isBlank(output)) {
            return "";
        }

        // 移除 markdown 代码块标记
        output = output.trim();
        if (output.startsWith("```sql")) {
            output = output.substring(6);
        } else if (output.startsWith("```")) {
            output = output.substring(3);
        }

        if (output.endsWith("```")) {
            output = output.substring(0, output.length() - 3);
        }

        return output.trim();
    }
}
