package com.agentar.scalesql.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库 Schema 元数据实体
 *
 * @author ScaleSQL Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("database_schemas")
public class DatabaseSchemaEntity {

    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * Schema 内容（JSON 格式）
     */
    private String schemaContent;

    /**
     * 创建时间
     */
    private java.time.LocalDateTime createTime;

    /**
     * 更新时间
     */
    private java.time.LocalDateTime updateTime;
}
