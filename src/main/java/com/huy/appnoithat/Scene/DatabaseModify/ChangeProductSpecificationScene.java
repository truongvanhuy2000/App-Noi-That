package com.huy.appnoithat.Scene.DatabaseModify;

import com.huy.appnoithat.Controller.DatabaseModify.ChangeProductSpecificationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class ChangeProductSpecificationScene {
    private Scene scene;
    private Parent root;
    private final FXMLLoader fxmlLoader;
    @Getter
    private static ChangeProductSpecificationController controller;
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
