package com.huy.appnoithat.Scene.LuaChonNoiThat;

import com.huy.appnoithat.Controller.NewTab.NewTabController;
import com.huy.appnoithat.Scene.GenericScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class NewTabScene implements GenericScene {
    private static final String viewPath = "/com/huy/appnoithat/Scene/view/TabLayout.fxml";
    private final Scene scene;
    private final Parent root;
    private final NewTabController newTabController;

    public NewTabScene(NewTabController newTabController) {
        this.newTabController = newTabController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(NewTabScene.class.getResource(viewPath));
            fxmlLoader.setController(newTabController);
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    private void addCssToScence() {
        String cssPath = "/com/huy/appnoithat/Scene/css/TabLayout.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
}
