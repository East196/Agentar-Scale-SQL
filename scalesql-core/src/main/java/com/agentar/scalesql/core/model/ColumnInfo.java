package com.agentar.scalesql.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 列信息模型
 *
 * @author ScaleSQL Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfo {

    /**
     * 列名
     */
    private String name;

    /**
     * 列类型
     */
    private String type;

    /**
     * 列描述
     */
    private String description;

    /**
     * 示例值列表
     */
    private List<Object> samples;
}
