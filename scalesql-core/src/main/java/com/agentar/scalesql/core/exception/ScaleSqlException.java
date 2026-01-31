package com.agentar.scalesql.core.exception;

/**
 * ScaleSQL 业务异常
 *
 * @author ScaleSQL Team
 */
public class ScaleSqlException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private String errorCode;

    public ScaleSqlException(String message) {
        super(message);
    }

    public ScaleSqlException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ScaleSqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScaleSqlException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
