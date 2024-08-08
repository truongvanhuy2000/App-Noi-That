package com.huy.appnoithat.Scene.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.Scene.GenericScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class LuaChonNoiThatScene implements GenericScene {
    private static final String VIEW_PATH = "/com/huy/appnoithat/Scene/view/LuaChonNoiThatLayout.fxml";
    private static final String CSS_PATH = "/com/huy/appnoithat/Scene/css/LuaChonNoiThatLayout.css";
    private Scene scene;
    private Parent root;
    private final LuaChonNoiThatController luaChonNoiThatController;

    public LuaChonNoiThatScene(LuaChonNoiThatController luaChonNoiThatController) {
        this.luaChonNoiThatController = luaChonNoiThatController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LuaChonNoiThatScene.class.getResource(VIEW_PATH));
            fxmlLoader.setController(luaChonNoiThatController);
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }

    public LuaChonNoiThatScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LuaChonNoiThatScene.class.getResource(VIEW_PATH));
            luaChonNoiThatController = new LuaChonNoiThatController();
            fxmlLoader.setController(luaChonNoiThatController);
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
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }
}
