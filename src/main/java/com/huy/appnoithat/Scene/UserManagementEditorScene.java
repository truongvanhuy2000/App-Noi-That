package com.huy.appnoithat.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class UserManagementEditorScene {
    private Scene scene;
    private Parent root;
    private static UserManagementEditorScene single_instance = null;
    private final FXMLLoader fxmlLoader;

    public UserManagementEditorScene() {
        String viewPath = "view/UserManagementEditorLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource(viewPath));
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

    public Scene getScene() {
        return scene;
    }

    // Create an object of this class, call this function
    public static synchronized UserManagementEditorScene getInstance() {
        if (single_instance == null)
            single_instance = new UserManagementEditorScene();
        return single_instance;
    }

    private void addCssToScence() {
        String cssPath = "css/UserManagementLayout.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
}
