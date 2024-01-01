package com.huy.appnoithat.Exception;

import com.huy.appnoithat.Common.PopupUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.NotificationPane;

import java.util.ArrayList;
import java.util.List;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    final static Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    public GlobalExceptionHandler() {
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        nestedLog(throwable);
        PopupUtils.throwErrorNotification("Critical Error! Please report back to the developer! \n");
    }
    private void nestedLog(Throwable throwable) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        List<String> traceList = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            LOGGER.error(stackTraceElement.toString());
            traceList.add(stackTraceElement.toString());
        }

    }
}
