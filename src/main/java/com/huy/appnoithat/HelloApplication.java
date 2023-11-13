package com.huy.appnoithat;

import com.huy.appnoithat.Exception.GlobalExceptionHandler;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Service.Configuration.configurationService;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Session.UserSession;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloApplication extends Application {
    final static Logger LOGGER = LogManager.getLogger(HelloApplication.class);
    UserSessionService sessionService = new UserSessionService();
    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler(stage));
            UserSession.getInstance();

            Scene scene = LoginScene.getInstance().getScene();
            Platform.runLater(() -> LoginScene.getInstance().getLoginController().init());
            StageFactory.closeAndCreateNewUnresizeableStage(stage, scene);

            if (sessionService.isLogin()) {
                scene = HomeScene.getInstance().getScene();
                Platform.runLater(() -> HomeScene.getInstance().getHomeController().init());
                StageFactory.closeAndCreateNewMaximizedStage(stage, scene);
            }
        });
    }
    public static void main(String[] args) {
        configurationService.readConfiguration();
        LOGGER.info("Start application");
        launch();
    }
}