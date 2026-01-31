package com.agentar.scalesql.data.mapper;

import com.agentar.scalesql.data.entity.DatabaseSchemaEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据库 Schema Mapper
 *
 * @author ScaleSQL Team
 */
@Mapper
public interface DatabaseSchemaMapper extends BaseMapper<DatabaseSchemaEntity> {
}
