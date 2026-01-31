package com.agentar.scalesql.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ScaleSQL 应用启动类
 *
 * @author ScaleSQL Team
 */
@SpringBootApplication(scanBasePackages = "com.agentar.scalesql")
public class ScaleSqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScaleSqlApplication.class, args);
    }
}
