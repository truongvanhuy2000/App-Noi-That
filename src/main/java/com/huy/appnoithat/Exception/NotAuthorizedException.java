package com.huy.appnoithat.Exception;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(String message) {
        super(message);
    }
    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }
    protected NotAuthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
