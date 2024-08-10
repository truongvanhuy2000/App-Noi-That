package com.huy.appnoithat.Scene.Login;

import com.huy.appnoithat.Controller.LoginController;
import com.huy.appnoithat.Scene.GenericScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class LoginScene implements GenericScene {
    private static final String VIEW_PATH = "/com/huy/appnoithat/Scene/view/LoginLayoutRemake.fxml";
    private static final String CSS_PATH = "/com/huy/appnoithat/Scene/css/LoginLayout.css";
    private Scene scene;
    private Parent root;
    private final FXMLLoader fxmlLoader;
    private final LoginController loginController;

    public LoginScene(LoginController loginController) {
        this.loginController = loginController;
        try {
            fxmlLoader = new FXMLLoader(LoginScene.class.getResource(VIEW_PATH));
            fxmlLoader.setController(loginController);
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    public void setRoot(Parent root) {
        this.root = root;
        scene.setRoot(this.root);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        scene.setRoot(this.root);
        addCssToScence();
    }

    private void addCssToScence() {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }
}
