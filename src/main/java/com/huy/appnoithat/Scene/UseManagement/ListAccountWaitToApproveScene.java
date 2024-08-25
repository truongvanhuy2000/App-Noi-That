package com.huy.appnoithat.Scene.UseManagement;

import com.huy.appnoithat.Scene.GenericScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class ListAccountWaitToApproveScene implements GenericScene {
    private final Scene scene;
    private final Parent root;
    private static final ListAccountWaitToApproveScene single_instance = null;
    private final FXMLLoader fxmlLoader;

    public ListAccountWaitToApproveScene() {
        String viewPath = "/com/huy/appnoithat/Scene/view/ListAccountWaitToApproveLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource(viewPath));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    private void addCssToScence() {
        String cssPath = "/com/huy/appnoithat/Scene/css/UserManagementLayout.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
}
