package com.huy.appnoithat.Scene.LuaChonNoiThat;

import com.huy.appnoithat.Controller.NewTab.NewTabController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class NewTabScene {
    private static final String viewPath = "/com/huy/appnoithat/Scene/view/TabLayout.fxml";
    private Scene scene;
    private Parent root;
    private final NewTabController newTabController;

    public NewTabScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(NewTabScene.class.getResource(viewPath));
            newTabController = new NewTabController();
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
