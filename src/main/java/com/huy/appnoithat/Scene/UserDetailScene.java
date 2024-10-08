package com.huy.appnoithat.Scene;

import com.huy.appnoithat.Controller.UserDetailController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class UserDetailScene implements GenericScene {
    private static final String VIEW_PATH = "/com/huy/appnoithat/Scene/view/UserDetail.fxml";
    private static final String CSS_PATH = "/com/huy/appnoithat/Scene/css/UserDetail.css";
    private final Scene scene;
    private final Parent root;
    private final UserDetailController controller;

    public UserDetailScene(UserDetailController controller) {
        this.controller = controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEW_PATH));
            fxmlLoader.setController(controller);
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    private void addCssToScence() {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }
}
