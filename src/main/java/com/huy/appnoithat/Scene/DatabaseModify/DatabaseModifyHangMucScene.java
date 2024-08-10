package com.huy.appnoithat.Scene.DatabaseModify;

import com.huy.appnoithat.Controller.DatabaseModify.DatabaseModifyHangMucController;
import com.huy.appnoithat.Scene.GenericScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class DatabaseModifyHangMucScene implements GenericScene {
    private Scene scene;
    private Parent root;
    private final FXMLLoader fxmlLoader;
    private final DatabaseModifyHangMucController controller;

    public DatabaseModifyHangMucScene(DatabaseModifyHangMucController controller) {
        this.controller = controller;
        String viewPath = "view/DatabaseModifyHangMucLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(DatabaseModifyHangMucScene.class.getResource(viewPath));
            fxmlLoader.setController(controller);
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    private void addCssToScence() {
        String cssPath = "css/DatabaseModifyPhongCach.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
}
