package com.huy.appnoithat.Scene.UseManagement;

import com.huy.appnoithat.Controller.UserManagement.UsersManagementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class UserManagementScene {
    private static final String VIEW_PATH = "/com/huy/appnoithat/Scene/view/UserManagementLayout.fxml";
    private static final String CSS_PATH = "/com/huy/appnoithat/Scene/css/UserManagementLayout.css";
    private Scene scene;
    private Parent root;
    @Getter
    private static UsersManagementController controller;

    public UserManagementScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEW_PATH));
            controller = new UsersManagementController();
            fxmlLoader.setController(controller);
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
