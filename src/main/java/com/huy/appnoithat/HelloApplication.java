package com.huy.appnoithat;

import com.huy.appnoithat.configuration.ConfigHandler;
import com.huy.appnoithat.Exception.GlobalExceptionHandler;
import com.huy.appnoithat.Handler.MultipleInstanceHandler;
import com.huy.appnoithat.IOC.DIContainer;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.Login.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Session.UserSessionManager;
import com.huy.appnoithat.Work.OpenFileWork;
import com.huy.appnoithat.Work.WorkFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class HelloApplication extends Application {
    final static Logger LOGGER = LogManager.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) {
        long previousTime = System.currentTimeMillis();
        DIContainer.createInjector();
        LOGGER.info("Create injector took {}", System.currentTimeMillis() - previousTime);
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
            LOGGER.info("Start application with params: " + list.get(0));
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
        launch(HelloApplication.class, args);
    }
}