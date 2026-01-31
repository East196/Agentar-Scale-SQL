package com.agentar.scalesql.llm.service;

import java.util.Map;

/**
 * LLM 服务接口
 * 封装大语言模型的调用
 *
 * @author ScaleSQL Team
 */
public interface LlmService {

    /**
     * 生成文本
     *
     * @param prompt      提示词
     * @param temperature 温度参数（0-2）
     * @param maxTokens   最大 token 数
     * @return 生成的文本
     */
    String generate(String prompt, Double temperature, Integer maxTokens);

    /**
     * 生成文本（使用默认参数）
     *
     * @param prompt 提示词
     * @return 生成的文本
     */
    String generate(String prompt);

    /**
     * 批量生成文本
     *
     * @param prompts     提示词列表
     * @param temperature 温度参数
     * @param maxTokens   最大 token 数
     * @return 生成的文本列表
     */
    java.util.List<String> batchGenerate(java.util.List<String> prompts, Double temperature, Integer maxTokens);

    /**
     * 使用思维链生成
     *
     * @param prompt         提示词
     * @param thinkingBudget 思考预算（token 数）
     * @return 生成的文本
     */
    String generateWithThinking(String prompt, Integer thinkingBudget);
}
