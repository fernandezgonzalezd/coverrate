package com.fernandez.david.coverrate.exception;

public class CoverrateException extends RuntimeException {
    public CoverrateException(String message) {
        super(message);
    }

    public CoverrateException(String message, Throwable cause) {
        super(message, cause);
    }
}
