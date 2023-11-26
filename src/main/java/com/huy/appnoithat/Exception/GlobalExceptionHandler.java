package com.huy.appnoithat.Exception;

import com.huy.appnoithat.Common.PopupUtils;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    final static Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    public GlobalExceptionHandler() {
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        nestedLog(throwable);
//        PopupUtils.throwCriticalError("Critical Error! Please report back to the developer! \n" + throwable.getMessage());
        if (throwable instanceof ServerConnectionException) {
            PopupUtils.throwCriticalError("Cannot connect to server! Please check your internet connection and try again!");
        }
        else if (throwable instanceof AccountExpiredException) {
            PopupUtils.throwErrorSignal("Critical Error! Please report back to the developer! \n" + throwable.getMessage());
        }
        else if (throwable instanceof NotAuthorizedException) {
            PopupUtils.throwErrorSignal(throwable.getMessage());
        }
        else {
            PopupUtils.throwCriticalError("Critical Error! Please report back to the developer! \n" + throwable.getMessage());
        }
        throwable.printStackTrace();

    }
    private void nestedLog(Throwable throwable) {
        if (throwable.getCause() != null) {
            nestedLog(throwable.getCause());
        }
        LOGGER.error(throwable.getMessage());
    }
}
