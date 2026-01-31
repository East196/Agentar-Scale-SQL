package com.agentar.scalesql.core.constant;

/**
 * 系统常量
 *
 * @author ScaleSQL Team
 */
public class Constants {

    /**
     * 默认字符集
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 数据库方言
     */
    public static class Dialect {
        public static final String SQLITE = "sqlite";
        public static final String POSTGRESQL = "postgresql";
        public static final String MYSQL = "mysql";
    }

    /**
     * LLM 模型名称
     */
    public static class Model {
        public static final String GPT_4 = "gpt-4";
        public static final String GPT_35_TURBO = "gpt-3.5-turbo";
        public static final String GEMINI = "gemini-pro";
        public static final String GEMINI_FLASH = "gemini-flash";
    }

    /**
     * 检索模式
     */
    public static class RetrievalMode {
        public static final String TEXT = "text";
        public static final String VECTOR = "vector";
        public static final String HYBRID = "hybrid";
    }
}
