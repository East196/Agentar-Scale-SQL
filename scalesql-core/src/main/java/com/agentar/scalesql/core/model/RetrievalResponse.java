package com.agentar.scalesql.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 检索响应模型
 *
 * @author ScaleSQL Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalResponse {

    /**
     * 检索到的文档列表
     */
    private List<Document> documents;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 文档模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Document {

        /**
         * 文档 ID
         */
        private String id;

        /**
         * 文档内容
         */
        private String content;

        /**
         * 相似度分数
         */
        private Double score;

        /**
         * 业务数据（元数据）
         */
        private java.util.Map<String, Object> metadata;
    }
}
