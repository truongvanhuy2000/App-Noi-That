package com.huy.appnoithat.Scene.DatabaseModify;

import com.huy.appnoithat.Controller.DatabaseModify.DatabaseModifyVatLieuController;
import com.huy.appnoithat.Scene.GenericScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class DatabaseModifyVatLieuScene implements GenericScene {
    private final Scene scene;
    private final Parent root;
    private final FXMLLoader fxmlLoader;
    private final DatabaseModifyVatLieuController controller;

    public DatabaseModifyVatLieuScene(DatabaseModifyVatLieuController controller) {
        this.controller = controller;
        String viewPath = "view/DatabaseModifyVatLieuLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(DatabaseModifyVatLieuScene.class.getResource(viewPath));
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
