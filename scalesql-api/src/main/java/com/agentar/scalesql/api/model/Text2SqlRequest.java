package com.agentar.scalesql.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Text-to-SQL 请求模型
 *
 * @author ScaleSQL Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Text2SqlRequest {

    /**
     * 自然语言问题
     */
    private String question;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 证据/提示信息（可选）
     */
    private String evidence;
}
