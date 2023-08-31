package com.huy.appnoithat;

import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import javafx.application.Application;
import javafx.stage.Stage;
import java.awt.im.InputContext;
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage){
        UserSessionService sessionService = new UserSessionService();
        if (sessionService.isLogin()){
            stage.setScene(HomeScene.getInstance().getScene());
        }
        else{
            stage.setScene(LoginScene.getInstance().getScene());

        }
        stage.setTitle("App Noi That");
        stage.show();
    }
    public static void main(String[] args){
        InputContext.getInstance();
        launch();
    }
}