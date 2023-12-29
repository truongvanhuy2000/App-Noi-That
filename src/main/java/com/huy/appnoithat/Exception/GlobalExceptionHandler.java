package com.huy.appnoithat.Exception;

import com.huy.appnoithat.Common.PopupUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.NotificationPane;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    final static Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    public GlobalExceptionHandler() {
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
//        throwable.printStackTrace();
        nestedLog(throwable);
        PopupUtils.throwErrorNotification("Critical Error! Please report back to the developer! \n");
    }
    private void nestedLog(Throwable throwable) {
        if (throwable.getCause() != null) {
            nestedLog(throwable.getCause());
        }
        LOGGER.error(throwable.getMessage());
    }
}
