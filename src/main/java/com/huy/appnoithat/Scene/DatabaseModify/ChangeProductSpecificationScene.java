package com.huy.appnoithat.Scene.DatabaseModify;

import com.huy.appnoithat.Controller.DatabaseModify.ChangeProductSpecificationController;
import com.huy.appnoithat.Scene.GenericScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class ChangeProductSpecificationScene implements GenericScene {
    private Scene scene;
    private Parent root;
    private FXMLLoader fxmlLoader;
    private ChangeProductSpecificationController controller;

    public ChangeProductSpecificationScene(ChangeProductSpecificationController controller) {
        this.controller = controller;
        String viewPath = "view/ChangeProductSpecificationLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(ChangeProductSpecificationScene.class.getResource(viewPath));
            fxmlLoader.setController(controller);
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    public ChangeProductSpecificationScene() {
        String viewPath = "view/ChangeProductSpecificationLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(ChangeProductSpecificationScene.class.getResource(viewPath));
            if (controller == null) {
                controller = new ChangeProductSpecificationController();
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
