package com.agentar.scalesql.core.schema;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.agentar.scalesql.core.model.ColumnInfo;
import com.agentar.scalesql.core.model.TableSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LightSchema 生成器
 * 用于生成轻量级的表结构描述
 *
 * @author ScaleSQL Team
 */
public class LightSchemaGenerator {

    private static final String LIGHT_SCHEMA_TEMPLATE =
        "## Table: %s\n" +
        "### Table description\n" +
        "%s\n" +
        "### Column information\n" +
        "%s";

    private static final String PRIMARY_KEY_TEMPLATE =
        "### Primary keys\n" +
        "%s";

    private static final String FOREIGN_KEY_TEMPLATE =
        "### Foreign keys\n" +
        "%s";

    private static final String INDEX_TEMPLATE =
        "### Index\n" +
        "%s";

    /**
     * 创建 LightSchema
     *
     * @param schema 表 Schema 信息
     * @return LightSchema 字符串
     */
    public static String createSchema(TableSchema schema) {
        List<String> schemaItems = new ArrayList<>();

        // 生成列信息表格
        String columnInformation = createColumnInformation(schema.getColumns());

        // 生成基础 Schema
        String baseSchema = String.format(
            LIGHT_SCHEMA_TEMPLATE,
            schema.getTable(),
            StrUtil.emptyToDefault(schema.getTableDescription(), ""),
            columnInformation
        );
        schemaItems.add(baseSchema);

        // 添加主键信息
        if (CollUtil.isNotEmpty(schema.getPrimaryKeys())) {
            String primaryKeyStr = String.join(", ", schema.getPrimaryKeys());
            schemaItems.add(String.format(PRIMARY_KEY_TEMPLATE, primaryKeyStr));
        }

        // 添加外键信息
        if (CollUtil.isNotEmpty(schema.getForeignKeys())) {
            String foreignKeyStr = String.join("\n", schema.getForeignKeys());
            schemaItems.add(String.format(FOREIGN_KEY_TEMPLATE, foreignKeyStr));
        }

        // 添加索引信息
        if (CollUtil.isNotEmpty(schema.getIndexes())) {
            String indexStr = String.join("\n", schema.getIndexes());
            schemaItems.add(String.format(INDEX_TEMPLATE, indexStr));
        }

        return String.join("\n", schemaItems);
    }

    /**
     * 创建列信息的 Markdown 表格
     *
     * @param columns 列信息列表
     * @return Markdown 表格字符串
     */
    private static String createColumnInformation(List<ColumnInfo> columns) {
        if (CollUtil.isEmpty(columns)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        // 表头
        sb.append("| column_name | column_type | column_description | value_examples |\n");
        sb.append("|-------------|-------------|-------------------|----------------|\n");

        // 表格内容
        for (ColumnInfo column : columns) {
            sb.append("| ")
              .append(column.getName())
              .append(" | ")
              .append(column.getType())
              .append(" | ")
              .append(StrUtil.emptyToDefault(column.getDescription(), ""))
              .append(" | ")
              .append(formatSamples(column.getSamples()))
              .append(" |\n");
        }

        return sb.toString();
    }

    /**
     * 格式化示例值列表
     *
     * @param samples 示例值列表
     * @return 格式化后的字符串
     */
    private static String formatSamples(List<Object> samples) {
        if (CollUtil.isEmpty(samples)) {
            return "";
        }

        return samples.stream()
            .map(Object::toString)
            .collect(Collectors.joining(", "));
    }
}
