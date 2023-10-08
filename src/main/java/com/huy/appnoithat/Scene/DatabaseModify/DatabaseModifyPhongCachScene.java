package com.huy.appnoithat.Scene.DatabaseModify;

import com.huy.appnoithat.Controller.DatabaseModify.DatabaseModifyPhongCachController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class DatabaseModifyPhongCachScene {
    private Scene scene;
    private Parent root;
    @Getter
    private static DatabaseModifyPhongCachController controller;
    public DatabaseModifyPhongCachScene() {
        String viewPath = "view/DatabaseModifyPhongCachLayout.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DatabaseModifyPhongCachScene.class.getResource(viewPath));
            if (controller == null) {
                controller = new DatabaseModifyPhongCachController();
            }
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
        String cssPath = "css/DatabaseModifyPhongCach.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
}
