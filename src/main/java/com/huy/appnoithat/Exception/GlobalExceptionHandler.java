package com.huy.appnoithat.Exception;

import com.huy.appnoithat.Common.PopupUtils;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    final static Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    private final Stage stage;

    public GlobalExceptionHandler(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        PopupUtils.throwCriticalError("Critical Error! Please report back to the developer! \n" + throwable.getMessage());
        throwable.printStackTrace();
        nestedLog(throwable);
    }
    private void nestedLog(Throwable throwable) {
        if (throwable.getCause() != null) {
            nestedLog(throwable.getCause());
        }
        LOGGER.error(throwable.getMessage());
    }
}
