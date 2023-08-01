package com.huy.appnoithat.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;

@Getter
public class LoginScreen {
    private final Scene loginScene;
    public LoginScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginLayout.fxml"));
        try {
            loginScene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
