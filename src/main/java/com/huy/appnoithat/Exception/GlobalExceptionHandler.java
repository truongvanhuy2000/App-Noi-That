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
//        PopupUtils.throwCriticalError("Critical Error! Please report back to the developer! \n" + throwable.getMessage());
        if (throwable instanceof ServerConnectionException) {
            PopupUtils.throwCriticalError(throwable.getLocalizedMessage());
        }
        else if (throwable instanceof AccountExpiredException) {
            PopupUtils.throwErrorSignal(throwable.getLocalizedMessage());
        }
        else if (throwable instanceof NotAuthorizedException) {
            PopupUtils.throwErrorSignal("Không được ủy quyền đăng nhập, kiểm tra lại tài khoản");
        }
        else {
            PopupUtils.throwCriticalError("Critical Error! Please report back to the developer! \n" + throwable.getMessage());
        }
        throwable.printStackTrace();
        LOGGER.error(throwable.getMessage());
    }
}
