package com.huy.appnoithat.Exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    final static Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    public GlobalExceptionHandler() {
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        nestedLog(throwable);
    }
    private void nestedLog(Throwable throwable) {
        LOGGER.error(throwable.getMessage());
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            LOGGER.error(stackTraceElement.toString());
        }
    }
}
