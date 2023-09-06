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
    private static ChangeProductSpecificationScene single_instance = null;
    public ChangeProductSpecificationScene() {
        String viewPath = "view/ChangeProductSpecificationLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(ChangeProductSpecificationScene.class.getResource(viewPath));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    // Create an object of this class, call this function
    public static synchronized ChangeProductSpecificationScene getInstance() {
        if (single_instance == null)
            single_instance = new ChangeProductSpecificationScene();
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
        String cssPath = "css/DatabaseModifyPhongCach.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
    public ChangeProductSpecificationController getController(){
        return fxmlLoader.getController();
    }
}
