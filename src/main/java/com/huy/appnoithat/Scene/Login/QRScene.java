package com.huy.appnoithat.Scene.Login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class QRScene {
    private static final String VIEW_PATH = "/com/huy/appnoithat/Scene/view/QRLayout.fxml";
    private static final String CSS_PATH = "/com/huy/appnoithat/Scene/css/UserManagementLayout.css";
    private Scene scene;
    private Parent root;
    private static QRScene single_instance = null;
    private final FXMLLoader fxmlLoader;

    public QRScene() {
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource(VIEW_PATH));
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

    // Create an object of this class, call this function
    public static synchronized QRScene getInstance() {
        if (single_instance == null)
            single_instance = new QRScene();
        return single_instance;
    }

    private void addCssToScence() {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }
}
