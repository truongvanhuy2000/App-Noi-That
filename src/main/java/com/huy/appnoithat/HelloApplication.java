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

import java.io.File;
import java.util.List;

public class HelloApplication extends Application {
    final static Logger LOGGER = LogManager.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());

        Platform.runLater(() -> {
            Parameters params = getParameters();
            List<String> list = params.getRaw();
            String fileToOpen = null;
            if (!list.isEmpty()) {
                LOGGER.info("Start application with params: " + list.get(0));
                fileToOpen = list.get(0);
            }
            UserSession.getInstance();
            UserSessionService sessionService = new UserSessionService();
            if (sessionService.isSessionValid()) {
                Scene scene = HomeScene.getInstance().getScene();
                Platform.runLater(() -> HomeScene.getInstance().getHomeController().init());
                StageFactory.closeAndCreateNewMaximizedStage(stage, scene);
                if (fileToOpen != null) {
                    HomeScene.getInstance().getHomeController().openNtFileWith(fileToOpen);
                }
            }
            else {
                LoginScene loginScene = new LoginScene();
                Scene scene = loginScene.getScene();
                Platform.runLater(() -> loginScene.getLoginController().init());
                StageFactory.closeAndCreateNewUnresizeableStage(stage, scene);
            }
        });
    }
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        configurationService.readConfiguration();
        LOGGER.info("Start application");
        launch(args);
    }
}