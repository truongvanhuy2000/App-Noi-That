package com.huy.appnoithat.Exception;

import com.huy.appnoithat.Shared.PopupUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    final static Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        PopupUtils.throwCriticalError("Critical Error! Please report back to the developer!");
        LOGGER.error("Unhandled exception caught! Thread: " + thread.getName() + " Message: " + throwable.getMessage());
        System.exit(1);
    }
}
