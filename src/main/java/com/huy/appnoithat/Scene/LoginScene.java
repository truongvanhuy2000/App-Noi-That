package com.huy.appnoithat.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class LoginScene {
    private Scene scene;
    private Parent root;
    private final FXMLLoader fxmlLoader;
    private static LoginScene single_instance = null;
    public LoginScene() {
        String viewPath = "view/LoginLayoutRemake.fxml";
        try {
            fxmlLoader = new FXMLLoader(LoginScene.class.getResource(viewPath));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    // Create an object of this class, call this function
    public static synchronized LoginScene getInstance() {
        if (single_instance == null)
            single_instance = new LoginScene();
        return single_instance;
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

    private void addCssToScence(){
        String cssPath = "css/LoginLayout.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
}
