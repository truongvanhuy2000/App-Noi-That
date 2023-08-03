package com.huy.appnoithat.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
@Getter
@Setter
public class UserManagementScene {
    private final Scene scene;
    private final Parent root;
    private static UserManagementScene single_instance = null;

    public UserManagementScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/UserManagementLayout.fxml"));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("css/UserManagementLayout.css").toExternalForm());
    }

    public static synchronized UserManagementScene getInstance() {
        if (single_instance == null)
            single_instance = new UserManagementScene();
        return single_instance;
    }

}
