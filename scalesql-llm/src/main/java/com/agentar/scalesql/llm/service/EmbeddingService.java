package com.agentar.scalesql.llm.service;

import java.util.List;

/**
 * 嵌入服务接口
 * 用于生成文本的向量表示
 *
 * @author ScaleSQL Team
 */
public interface EmbeddingService {

    /**
     * 生成单个文本的嵌入向量
     *
     * @param text 文本
     * @return 嵌入向量
     */
    float[] embed(String text);

    /**
     * 批量生成嵌入向量
     *
     * @param texts 文本列表
     * @return 嵌入向量列表
     */
    List<float[]> batchEmbed(List<String> texts);

    /**
     * 计算两个向量的余弦相似度
     *
     * @param vector1 向量1
     * @param vector2 向量2
     * @return 相似度（0-1）
     */
    double cosineSimilarity(float[] vector1, float[] vector2);
}
