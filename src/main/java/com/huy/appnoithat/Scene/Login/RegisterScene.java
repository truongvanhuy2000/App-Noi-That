package com.huy.appnoithat.Scene.Login;

import com.huy.appnoithat.Controller.Register.RegisterController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class RegisterScene {
    private static final String VIEW_PATH = "/com/huy/appnoithat/Scene/view/RegisterLayout.fxml";
    private static final String CSS_PATH = "/com/huy/appnoithat/Scene/css/UserManagementLayout.css";
    private Scene scene;
    private Parent root;
    private final FXMLLoader fxmlLoader;
    @Getter
    private static RegisterController registerController;

    public RegisterScene() {
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource(VIEW_PATH));
            if (registerController == null) {
                registerController = new RegisterController();
            }
            fxmlLoader.setController(registerController);
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

    private void addCssToScence() {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }
}
