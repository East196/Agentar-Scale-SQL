package com.agentar.scalesql.core.schema;

import com.agentar.scalesql.core.model.ColumnInfo;
import com.agentar.scalesql.core.model.TableSchema;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * LightSchemaGenerator 测试类
 *
 * @author ScaleSQL Team
 */
public class LightSchemaGeneratorTest {

    @Test
    public void testCreateSchema() {
        // 准备测试数据
        List<ColumnInfo> columns = Arrays.asList(
            ColumnInfo.builder()
                .name("id")
                .type("INTEGER")
                .description("用户ID")
                .samples(Arrays.asList(1, 2, 3))
                .build(),
            ColumnInfo.builder()
                .name("name")
                .type("VARCHAR")
                .description("用户名")
                .samples(Arrays.asList("张三", "李四", "王五"))
                .build(),
            ColumnInfo.builder()
                .name("age")
                .type("INTEGER")
                .description("年龄")
                .samples(Arrays.asList(25, 30, 35))
                .build()
        );

        TableSchema schema = TableSchema.builder()
            .database("test_db")
            .table("users")
            .tableDescription("用户表")
            .columns(columns)
            .primaryKeys(Arrays.asList("id"))
            .foreignKeys(Arrays.asList("department_id references departments(id)"))
            .build();

        // 生成 LightSchema
        String lightSchema = LightSchemaGenerator.createSchema(schema);

        // 验证结果
        assertNotNull(lightSchema);
        assertTrue(lightSchema.contains("## Table: users"));
        assertTrue(lightSchema.contains("用户表"));
        assertTrue(lightSchema.contains("column_name"));
        assertTrue(lightSchema.contains("id"));
        assertTrue(lightSchema.contains("name"));
        assertTrue(lightSchema.contains("age"));
        assertTrue(lightSchema.contains("### Primary keys"));
        assertTrue(lightSchema.contains("### Foreign keys"));

        // 打印结果
        System.out.println("生成的 LightSchema:");
        System.out.println(lightSchema);
    }

    @Test
    public void testCreateSchemaWithoutOptionalFields() {
        // 测试没有主键、外键、索引的情况
        List<ColumnInfo> columns = Arrays.asList(
            ColumnInfo.builder()
                .name("id")
                .type("INTEGER")
                .samples(Arrays.asList(1, 2, 3))
                .build()
        );

        TableSchema schema = TableSchema.builder()
            .database("test_db")
            .table("simple_table")
            .columns(columns)
            .build();

        String lightSchema = LightSchemaGenerator.createSchema(schema);

        assertNotNull(lightSchema);
        assertTrue(lightSchema.contains("## Table: simple_table"));
        assertFalse(lightSchema.contains("### Primary keys"));
        assertFalse(lightSchema.contains("### Foreign keys"));
    }
}
