package com.huy.appnoithat.Scene;

import com.huy.appnoithat.Controller.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;
@Getter
public class HomeScene {
    private Scene scene;
    private Parent root;
    private final FXMLLoader fxmlLoader;
    private static HomeScene single_instance = null;
    public HomeScene() {
        String viewPath = "view/HomeLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(HomeScene.class.getResource(viewPath));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    // Create an object of this class, call this function
    public static synchronized HomeScene getInstance() {
        if (single_instance == null)
            single_instance = new HomeScene();
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
        String cssPath = "css/HomeLayout.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
    public HomeController getHomeController(){
        return fxmlLoader.getController();
    }
}
