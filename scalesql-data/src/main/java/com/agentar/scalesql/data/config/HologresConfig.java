package com.agentar.scalesql.data.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Hologres 数据源配置
 *
 * @author ScaleSQL Team
 */
@Configuration
@ConfigurationProperties(prefix = "hologres")
@Data
public class HologresConfig {

    /**
     * JDBC URL
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 驱动类名
     */
    private String driverClassName = "org.postgresql.Driver";

    /**
     * 初始连接数
     */
    private int initialSize = 5;

    /**
     * 最小空闲连接数
     */
    private int minIdle = 5;

    /**
     * 最大活跃连接数
     */
    private int maxActive = 20;

    /**
     * 配置获取连接等待超时的时间
     */
    private long maxWait = 60000;

    /**
     * 创建 Hologres 数据源
     */
    @Bean(name = "hologresDataSource")
    public DataSource hologresDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);

        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);

        // 用来检测连接是否有效的 SQL
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        return dataSource;
    }

    /**
     * 创建 JdbcTemplate
     */
    @Bean(name = "hologresJdbcTemplate")
    public JdbcTemplate hologresJdbcTemplate(DataSource hologresDataSource) {
        return new JdbcTemplate(hologresDataSource);
    }
}
