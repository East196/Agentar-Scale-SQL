package com.agentar.scalesql.api.controller;

import com.agentar.scalesql.api.model.Text2SqlRequest;
import com.agentar.scalesql.api.model.Text2SqlResponse;
import com.agentar.scalesql.api.service.Text2SqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Text-to-SQL API 控制器
 *
 * @author ScaleSQL Team
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/text2sql")
public class Text2SqlController {

    @Resource
    private Text2SqlService text2SqlService;

    /**
     * 将自然语言问题转换为 SQL
     */
    @PostMapping("/generate")
    public Text2SqlResponse generateSql(@RequestBody Text2SqlRequest request) {
        log.info("收到 Text-to-SQL 请求，问题: {}", request.getQuestion());

        try {
            String sql = text2SqlService.generateSql(
                request.getQuestion(),
                request.getDatabaseName(),
                request.getEvidence()
            );

            return Text2SqlResponse.builder()
                .success(true)
                .sql(sql)
                .message("SQL 生成成功")
                .build();

        } catch (Exception e) {
            log.error("SQL 生成失败", e);
            return Text2SqlResponse.builder()
                .success(false)
                .message("SQL 生成失败: " + e.getMessage())
                .build();
        }
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
