package com.maizhiyu.yzt.exception;

public class HisException extends RuntimeException {

    public HisException() {
    }

    public HisException(String message) {
        super(message);
    }

    public HisException(String message, Throwable cause) {
        super(message, cause);
    }

    public HisException(Throwable cause) {
        super(cause);
    }

    public HisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
