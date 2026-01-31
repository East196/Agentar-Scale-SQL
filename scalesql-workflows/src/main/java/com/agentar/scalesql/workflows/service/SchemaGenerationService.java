package com.agentar.scalesql.workflows.service;

import com.agentar.scalesql.core.model.TableSchema;

import java.util.Map;

/**
 * Schema 生成服务接口
 *
 * @author ScaleSQL Team
 */
public interface SchemaGenerationService {

    /**
     * 生成数据库的 LightSchema
     *
     * @param databaseName 数据库名称
     * @param metadata     元数据
     * @return Schema 映射（表名 -> LightSchema）
     */
    Map<String, String> generateLightSchema(String databaseName, Map<String, Object> metadata);

    /**
     * 从数据库元数据生成 TableSchema
     *
     * @param databaseName 数据库名称
     * @param tableName    表名
     * @param metadata     元数据
     * @return TableSchema
     */
    TableSchema generateTableSchema(String databaseName, String tableName, Map<String, Object> metadata);
}
