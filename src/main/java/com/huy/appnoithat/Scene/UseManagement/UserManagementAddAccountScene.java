package com.huy.appnoithat.Scene.UseManagement;

import com.huy.appnoithat.Scene.GenericScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class UserManagementAddAccountScene implements GenericScene {
    private Scene scene;
    private Parent root;
    private static UserManagementAddAccountScene single_instance = null;
    private final FXMLLoader fxmlLoader;

    public UserManagementAddAccountScene() {
        String viewPath = "/com/huy/appnoithat/Scene/view/UserManagementAddAccountLayout.fxml";
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
    public static synchronized UserManagementAddAccountScene getInstance() {
        if (single_instance == null)
            single_instance = new UserManagementAddAccountScene();
        return single_instance;
    }

    private void addCssToScence() {
        String cssPath = "/com/huy/appnoithat/Scene/css/UserManagementAddAccount.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
}
