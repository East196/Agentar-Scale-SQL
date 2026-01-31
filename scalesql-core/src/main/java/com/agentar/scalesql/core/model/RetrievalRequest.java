package com.agentar.scalesql.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 检索请求模型
 *
 * @author ScaleSQL Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalRequest {

    /**
     * 检索查询文本
     */
    private String searchQuery;

    /**
     * 检索模式（text/vector/hybrid）
     */
    private String mode;

    /**
     * 相似度阈值
     */
    private Double threshold;

    /**
     * 返回结果数量
     */
    private Integer topK;

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 额外的元数据过滤条件
     */
    private Map<String, Object> filters;
}
