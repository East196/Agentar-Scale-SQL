package com.agentar.scalesql.api.service;

/**
 * Text-to-SQL 服务接口
 *
 * @author ScaleSQL Team
 */
public interface Text2SqlService {

    /**
     * 生成 SQL
     *
     * @param question     自然语言问题
     * @param databaseName 数据库名称
     * @param evidence     证据/提示信息
     * @return 生成的 SQL
     */
    String generateSql(String question, String databaseName, String evidence);
}
