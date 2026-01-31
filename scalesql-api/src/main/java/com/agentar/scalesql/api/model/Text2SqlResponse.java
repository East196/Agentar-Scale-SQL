package com.agentar.scalesql.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Text-to-SQL 响应模型
 *
 * @author ScaleSQL Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Text2SqlResponse {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 生成的 SQL
     */
    private String sql;

    /**
     * 消息
     */
    private String message;

    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;
}
