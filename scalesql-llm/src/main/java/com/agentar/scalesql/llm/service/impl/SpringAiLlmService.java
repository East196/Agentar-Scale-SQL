package com.agentar.scalesql.llm.service.impl;

import com.agentar.scalesql.core.exception.ScaleSqlException;
import com.agentar.scalesql.llm.service.LlmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于 Spring AI 的 LLM 服务实现
 *
 * @author ScaleSQL Team
 */
@Slf4j
@Service
public class SpringAiLlmService implements LlmService {

    @Resource
    private ChatClient chatClient;

    /**
     * 默认温度
     */
    private static final Double DEFAULT_TEMPERATURE = 0.7;

    /**
     * 默认最大 token 数
     */
    private static final Integer DEFAULT_MAX_TOKENS = 2000;

    @Override
    public String generate(String prompt, Double temperature, Integer maxTokens) {
        try {
            log.debug("生成文本，prompt 长度: {}, temperature: {}, maxTokens: {}",
                prompt.length(), temperature, maxTokens);

            // 构建选项
            OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withTemperature(temperature.floatValue())
                .withMaxTokens(maxTokens)
                .build();

            // 创建消息
            Message message = new UserMessage(prompt);
            Prompt chatPrompt = new Prompt(List.of(message), options);

            // 调用 LLM
            ChatResponse response = chatClient.call(chatPrompt);

            String result = response.getResult().getOutput().getContent();
            log.debug("生成完成，结果长度: {}", result.length());

            return result;

        } catch (Exception e) {
            log.error("LLM 生成失败", e);
            throw new ScaleSqlException("LLM 生成失败", e);
        }
    }

    @Override
    public String generate(String prompt) {
        return generate(prompt, DEFAULT_TEMPERATURE, DEFAULT_MAX_TOKENS);
    }

    @Override
    public List<String> batchGenerate(List<String> prompts, Double temperature, Integer maxTokens) {
        log.info("批量生成文本，数量: {}", prompts.size());

        List<String> results = new ArrayList<>();
        for (String prompt : prompts) {
            try {
                String result = generate(prompt, temperature, maxTokens);
                results.add(result);
            } catch (Exception e) {
                log.error("批量生成失败，prompt: {}", prompt.substring(0, Math.min(100, prompt.length())), e);
                results.add("");
            }
        }

        return results;
    }

    @Override
    public String generateWithThinking(String prompt, Integer thinkingBudget) {
        log.debug("使用思维链生成，thinking budget: {}", thinkingBudget);

        try {
            // 构建带思维链的选项
            OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withTemperature(DEFAULT_TEMPERATURE.floatValue())
                .withMaxTokens(DEFAULT_MAX_TOKENS + thinkingBudget)
                .build();

            // 添加思维链提示
            String enhancedPrompt = "Let's think step by step.\n\n" + prompt;

            Message message = new UserMessage(enhancedPrompt);
            Prompt chatPrompt = new Prompt(List.of(message), options);

            ChatResponse response = chatClient.call(chatPrompt);
            return response.getResult().getOutput().getContent();

        } catch (Exception e) {
            log.error("思维链生成失败", e);
            throw new ScaleSqlException("思维链生成失败", e);
        }
    }
}
