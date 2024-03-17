package com.huy.appnoithat;

import com.huy.appnoithat.Configuration.ConfigHandler;
import com.huy.appnoithat.Exception.GlobalExceptionHandler;
import com.huy.appnoithat.Handler.MultipleInstanceHandler;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.Login.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Session.UserSession;
import com.huy.appnoithat.Work.OpenFileWork;
import com.huy.appnoithat.Work.WorkFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class HelloApplication extends Application {
    final static Logger LOGGER = LogManager.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        getPendingWorkAtLaunch();
        Platform.runLater(() -> {
            startUI(stage);
        });
    }
    private void startUI(Stage stage) {
        UserSession.getInstance();
        UserSessionService sessionService = new UserSessionService();
        if (sessionService.isSessionValid()) {
            Scene scene = HomeScene.getInstance().getScene();
            Stage mainStage = StageFactory.createNewMaximizedMainStage(stage, scene, true);
            Platform.runLater(() -> HomeScene.getInstance().getHomeController().init(mainStage));
        } else {
            LoginScene loginScene = new LoginScene();
            Scene scene = loginScene.getScene();
            Platform.runLater(() -> loginScene.getLoginController().init());
            StageFactory.createNewUnResizeableMainStage(stage, scene, true);
        }
    }
    private void getPendingWorkAtLaunch() {
        Parameters params = getParameters();
        List<String> list = params.getRaw();
        if (!list.isEmpty()) {
            LOGGER.info("Start application with params: " + list.get(0));
            String fileToOpen = list.get(0);
            WorkFactory.addNewOpenFileWork(new OpenFileWork(fileToOpen));
        }
    }
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        if (!MultipleInstanceHandler.isSingleInstance(args)) {
            System.exit(0);
        }
        MultipleInstanceHandler.startHandleMultipleInstance();
        LOGGER.info("Start application");
        ConfigHandler.getInstance().readConfiguration();
        launch(args);
    }
}