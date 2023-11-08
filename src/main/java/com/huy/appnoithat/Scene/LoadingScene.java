package com.huy.appnoithat.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
@Getter
@Setter
public class LoadingScene {
    private static final String VIEW_PATH = "/com/huy/appnoithat/Scene/view/Loading.fxml";
    private Scene scene;
    private Parent root;
    private final FXMLLoader fxmlLoader;
    private static LoadingScene single_instance;
    public LoadingScene() {
        try {
            fxmlLoader = new FXMLLoader(LoadingScene.class.getResource(VIEW_PATH));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
    }
    public static synchronized LoadingScene getInstance() {
        if (single_instance == null)
            single_instance = new LoadingScene();
        return single_instance;
    }
}
