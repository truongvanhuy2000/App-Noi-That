package com.huy.appnoithat.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
@Getter
@Setter
public class LuaChonNoiThatScene {
    private final Scene scene;
    private final Parent root;
    private static LuaChonNoiThatScene single_instance = null;

    public LuaChonNoiThatScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LuaChonNoiThatScene.class.getResource("view/LuaChonNoiThatLayout.fxml"));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("css/LuaChonNoiThatLayout.css").toExternalForm());
    }

    public static synchronized LuaChonNoiThatScene getInstance() {
        if (single_instance == null)
            single_instance = new LuaChonNoiThatScene();
        return single_instance;
    }
}
