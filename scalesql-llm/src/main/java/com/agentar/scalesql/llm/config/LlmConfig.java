package com.agentar.scalesql.llm.config;

import lombok.Data;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LLM 配置类
 *
 * @author ScaleSQL Team
 */
@Configuration
@ConfigurationProperties(prefix = "llm")
@Data
public class LlmConfig {

    /**
     * OpenAI API Key
     */
    private String apiKey;

    /**
     * API Base URL
     */
    private String baseUrl = "https://api.openai.com";

    /**
     * 默认模型
     */
    private String model = "gpt-3.5-turbo";

    /**
     * 超时时间（秒）
     */
    private Integer timeout = 60;

    /**
     * 创建 ChatClient
     */
    @Bean
    public ChatClient chatClient() {
        OpenAiApi openAiApi = new OpenAiApi(baseUrl, apiKey);
        return new OpenAiChatClient(openAiApi);
    }
}
