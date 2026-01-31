package com.agentar.scalesql.workflows.service;

import java.util.List;
import java.util.Map;

/**
 * 检索服务接口
 * 封装数据库单元检索和骨架检索
 *
 * @author ScaleSQL Team
 */
public interface RetrievalService {

    /**
     * 数据库单元检索
     * 根据问题中的关键词检索相关的数据库单元（表、列、值）
     *
     * @param databaseLiterals 数据库字面值列表
     * @param threshold        相似度阈值
     * @param topK             返回结果数量
     * @return 检索结果
     */
    List<CellRetrievalResult> retrieveDatabaseCells(
        List<String> databaseLiterals,
        Double threshold,
        Integer topK
    );

    /**
     * 骨架检索
     * 检索相似的 SQL 示例
     *
     * @param questionSkeleton 问题骨架
     * @param threshold        相似度阈值
     * @param topK             返回结果数量
     * @return 检索结果
     */
    List<SkeletonRetrievalResult> retrieveSkeleton(
        String questionSkeleton,
        Double threshold,
        Integer topK
    );

    /**
     * 数据库单元检索结果
     */
    class CellRetrievalResult {
        private String table;
        private String column;
        private String content;
        private Double score;

        public CellRetrievalResult(String table, String column, String content, Double score) {
            this.table = table;
            this.column = column;
            this.content = content;
            this.score = score;
        }

        public String getTable() {
            return table;
        }

        public String getColumn() {
            return column;
        }

        public String getContent() {
            return content;
        }

        public Double getScore() {
            return score;
        }
    }

    /**
     * 骨架检索结果
     */
    class SkeletonRetrievalResult {
        private String question;
        private String evidence;
        private String sql;
        private Double score;

        public SkeletonRetrievalResult(String question, String evidence, String sql, Double score) {
            this.question = question;
            this.evidence = evidence;
            this.sql = sql;
            this.score = score;
        }

        public String getQuestion() {
            return question;
        }

        public String getEvidence() {
            return evidence;
        }

        public String getSql() {
            return sql;
        }

        public Double getScore() {
            return score;
        }

        public String format() {
            return String.format("Question: %s\nEvidence: %s\nSQL: %s", question, evidence, sql);
        }
    }
}
