package com.huy.appnoithat.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class LoginScene {
    private final Scene scene;
    private final Parent root;
    private static LoginScene single_instance = null;
    public LoginScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginScene.class.getResource("view/LoginLayout.fxml"));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("css/LoginLayout.css").toExternalForm());
    }
    public static synchronized LoginScene getInstance() {
        if (single_instance == null)
            single_instance = new LoginScene();
        return single_instance;
    }
}
