package com.huy.appnoithat.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class LuaChonNoiThatScene {
    private Scene scene;
    private Parent root;
    private static LuaChonNoiThatScene single_instance = null;
    private final FXMLLoader fxmlLoader;
    public LuaChonNoiThatScene() {
        String viewPath = "view/LuaChonNoiThatLayout.fxml";
        try {
            fxmlLoader = new FXMLLoader(LuaChonNoiThatScene.class.getResource(viewPath));
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScence();
    }
    // Create an object of this class, call this function
    public static synchronized LuaChonNoiThatScene getInstance() {
        if (single_instance == null)
            single_instance = new LuaChonNoiThatScene();
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
        String cssPath = "css/LuaChonNoiThatLayout.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
}
