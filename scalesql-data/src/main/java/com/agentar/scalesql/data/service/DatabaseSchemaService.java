package com.agentar.scalesql.data.service;

import com.agentar.scalesql.data.entity.DatabaseSchemaEntity;

import java.util.List;

/**
 * 数据库 Schema 服务接口
 *
 * @author ScaleSQL Team
 */
public interface DatabaseSchemaService {

    /**
     * 保存 Schema
     *
     * @param entity Schema 实体
     * @return 是否成功
     */
    boolean saveSchema(DatabaseSchemaEntity entity);

    /**
     * 根据数据库名称获取 Schema
     *
     * @param databaseName 数据库名称
     * @return Schema 实体
     */
    DatabaseSchemaEntity getSchemaByDatabaseName(String databaseName);

    /**
     * 获取所有 Schema
     *
     * @return Schema 列表
     */
    List<DatabaseSchemaEntity> getAllSchemas();

    /**
     * 删除 Schema
     *
     * @param databaseName 数据库名称
     * @return 是否成功
     */
    boolean deleteSchema(String databaseName);
}
