package com.huy.appnoithat;

import com.huy.appnoithat.Configuration.ConfigHandler;
import com.huy.appnoithat.Exception.GlobalExceptionHandler;
import com.huy.appnoithat.Handler.HandleOldBackupFile;
import com.huy.appnoithat.Handler.MultipleInstanceHandler;
import com.huy.appnoithat.IOC.DIContainer;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.Login.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Session.UserSessionManager;
import com.huy.appnoithat.Work.OpenFileWork;
import com.huy.appnoithat.Work.WorkFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class HelloApplication extends Application {
    final static Logger LOGGER = LogManager.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) {
        DIContainer.createInjector();
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        getPendingWorkAtLaunch();
        startUI(stage);
    }

    private void startUI(Stage stage) {
        UserSessionManager sessionService = DIContainer.get();
        if (sessionService.isSessionValid()) {
            HomeScene homeScene = DIContainer.get();
            Stage mainStage = StageFactory.createNewMaximizedMainStage(stage, homeScene, true);
            homeScene.getHomeController().init(mainStage);
        } else {
            LoginScene loginScene = DIContainer.get();
            loginScene.getLoginController().init();
            StageFactory.createNewUnResizeableMainStage(stage, loginScene, true);
        }
    }

    private void getPendingWorkAtLaunch() {
        Parameters params = getParameters();
        List<String> list = params.getRaw();
        if (!list.isEmpty()) {
            LOGGER.info("Start application with params: {}", list.get(0));
            String fileToOpen = list.get(0);
            WorkFactory.addNewOpenFileWork(new OpenFileWork(fileToOpen));
        }
    }

    public static void main(String[] args) {
        if (!MultipleInstanceHandler.isSingleInstance(args)) {
            System.exit(0);
        }
        MultipleInstanceHandler.startHandleMultipleInstance();
        LOGGER.info("Start application");
        ConfigHandler.getInstance().readConfiguration();
        HandleOldBackupFile.start();
        launch(HelloApplication.class, args);
    }
}