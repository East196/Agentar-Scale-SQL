package com.agentar.scalesql.data.service.impl;

import com.agentar.scalesql.data.entity.DatabaseSchemaEntity;
import com.agentar.scalesql.data.mapper.DatabaseSchemaMapper;
import com.agentar.scalesql.data.service.DatabaseSchemaService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据库 Schema 服务实现
 *
 * @author ScaleSQL Team
 */
@Slf4j
@Service
public class DatabaseSchemaServiceImpl implements DatabaseSchemaService {

    @Resource
    private DatabaseSchemaMapper databaseSchemaMapper;

    @Override
    public boolean saveSchema(DatabaseSchemaEntity entity) {
        // 检查是否已存在
        DatabaseSchemaEntity existing = getSchemaByDatabaseName(entity.getDatabaseName());

        if (existing != null) {
            // 更新
            entity.setId(existing.getId());
            entity.setUpdateTime(LocalDateTime.now());
            return databaseSchemaMapper.updateById(entity) > 0;
        } else {
            // 新增
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            return databaseSchemaMapper.insert(entity) > 0;
        }
    }

    @Override
    public DatabaseSchemaEntity getSchemaByDatabaseName(String databaseName) {
        LambdaQueryWrapper<DatabaseSchemaEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DatabaseSchemaEntity::getDatabaseName, databaseName);
        return databaseSchemaMapper.selectOne(wrapper);
    }

    @Override
    public List<DatabaseSchemaEntity> getAllSchemas() {
        return databaseSchemaMapper.selectList(null);
    }

    @Override
    public boolean deleteSchema(String databaseName) {
        LambdaQueryWrapper<DatabaseSchemaEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DatabaseSchemaEntity::getDatabaseName, databaseName);
        return databaseSchemaMapper.delete(wrapper) > 0;
    }
}
