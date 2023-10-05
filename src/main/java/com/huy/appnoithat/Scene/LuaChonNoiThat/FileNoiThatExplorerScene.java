package com.huy.appnoithat.Scene.LuaChonNoiThat;

import com.huy.appnoithat.Controller.FileNoiThatExplorer.FileNoiThatExplorerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;
@Getter
public class FileNoiThatExplorerScene {
    private static final String VIEW_PATH = "/com/huy/appnoithat/Scene/view/FileNoiThatExplorer.fxml";
    private static final String CSS_PATH = "/com/huy/appnoithat/Scene/css/FileNoiThatExplorer.css";
    private Scene scene;
    private Parent root;
    private static FileNoiThatExplorerScene single_instance = null;
    private final FXMLLoader fxmlLoader;
    public FileNoiThatExplorerScene() {
        try {
            this.fxmlLoader = new FXMLLoader(FileNoiThatExplorerScene.class.getResource(VIEW_PATH));
            root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addCssToScence();
    }

    // Create an object of this class, call this function
    public static synchronized FileNoiThatExplorerScene getInstance() {
        if (single_instance == null)
            single_instance = new FileNoiThatExplorerScene();
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

    private void addCssToScence() {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }
    public FileNoiThatExplorerController getController() {
        return fxmlLoader.getController();
    }
}
