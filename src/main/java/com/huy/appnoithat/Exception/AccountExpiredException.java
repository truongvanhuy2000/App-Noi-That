package com.huy.appnoithat.Exception;

public class AccountExpiredException extends RuntimeException {
    public AccountExpiredException(String message) {
        super(message);
    }
    public AccountExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
    public AccountExpiredException(Throwable cause) {
        super(cause);
    }
    protected AccountExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
