package com.huy.appnoithat.Exception;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Scene.LoginScene;
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
        PopupUtils.throwCriticalError("Critical Error! Please report back to the developer!");
        throwable.printStackTrace();
        LOGGER.error("Unhandled exception caught! Thread: " + thread.getName() + " Message: " + throwable.getMessage());
        nestedLog(throwable);
//        LOGGER.error(throwable.fillInStackTrace().toString());
        stage.setScene(LoginScene.getInstance().getScene());
        stage.show();
    }
    private void nestedLog(Throwable throwable) {
        if (throwable.getCause() != null) {
            nestedLog(throwable.getCause());
        }
        LOGGER.error(throwable.getMessage());
    }
}
