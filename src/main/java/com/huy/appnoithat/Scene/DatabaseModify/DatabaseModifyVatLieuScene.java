package com.huy.appnoithat.Scene.DatabaseModify;

import com.huy.appnoithat.Controller.DatabaseModify.DatabaseModifyVatLieuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class DatabaseModifyVatLieuScene {
    private Scene scene;
    private Parent root;
    private final FXMLLoader fxmlLoader;
    private static DatabaseModifyVatLieuScene single_instance = null;

    public DatabaseModifyVatLieuScene() {
        String viewPath = "view/DatabaseModifyVatLieuLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(DatabaseModifyVatLieuScene.class.getResource(viewPath));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    // Create an object of this class, call this function
    public static synchronized DatabaseModifyVatLieuScene getInstance() {
        if (single_instance == null)
            single_instance = new DatabaseModifyVatLieuScene();
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
        String cssPath = "css/DatabaseModifyPhongCach.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }

    public DatabaseModifyVatLieuController getController() {
        return fxmlLoader.getController();
    }
}
