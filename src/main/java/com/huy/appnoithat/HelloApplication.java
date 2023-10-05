package com.huy.appnoithat;

import com.huy.appnoithat.Exception.GlobalExceptionHandler;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Session.UserSession;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloApplication extends Application {
    final static Logger LOGGER = LogManager.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler(stage));
        UserSession.getInstance();
        UserSessionService sessionService = new UserSessionService();
        if (sessionService.isLogin()) {
            stage.setScene(HomeScene.getInstance().getScene());
        } else {
            stage.setScene(LoginScene.getInstance().getScene());
        }
        stage.setTitle("App Noi That");
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/logoapp.jpg")));
        stage.show();
    }

    public static void main(String[] args) {
        LOGGER.info("Start application");
        launch();
    }
}