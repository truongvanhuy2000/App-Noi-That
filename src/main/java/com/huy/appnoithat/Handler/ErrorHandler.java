package com.huy.appnoithat.Handler;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

import java.util.function.Consumer;

public class ErrorHandler {
    final static Logger LOGGER = LogManager.getLogger(ErrorHandler.class);
    public static void handleTokenExpired(Runnable aditionalAction) {
        LOGGER.error("Token expired");
        aditionalAction.run();
        PopupUtils.throwErrorSignal("Token expired! Please login again!");
        Platform.exit();
        Platform.runLater(() -> {
            LoginScene loginScene = LoginScene.getInstance();
            loginScene.getLoginController().init();
            StageFactory.CreateNewUnresizeableStage(loginScene.getScene());
        });
    }
}
