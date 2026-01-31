package com.agentar.scalesql.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表 Schema 信息模型
 *
 * @author ScaleSQL Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableSchema {

    /**
     * 数据库名
     */
    private String database;

    /**
     * 表名
     */
    private String table;

    /**
     * 数据库描述
     */
    private String databaseDescription;

    /**
     * 表描述
     */
    private String tableDescription;

    /**
     * 列信息列表
     */
    private List<ColumnInfo> columns;

    /**
     * 主键列表
     */
    private List<String> primaryKeys;

    /**
     * 外键列表
     */
    private List<String> foreignKeys;

    /**
     * 索引列表
     */
    private List<String> indexes;
}
