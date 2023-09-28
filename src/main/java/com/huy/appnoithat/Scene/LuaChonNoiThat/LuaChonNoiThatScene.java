package com.huy.appnoithat.Scene.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class LuaChonNoiThatScene {
    private static final String VIEW_PATH = "/com/huy/appnoithat/Scene/view/LuaChonNoiThatLayout.fxml";
    private static final String CSS_PATH = "/com/huy/appnoithat/Scene/css/LuaChonNoiThatLayout.css";
    private Scene scene;
    private Parent root;
    private static LuaChonNoiThatScene single_instance = null;

    public LuaChonNoiThatScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LuaChonNoiThatScene.class.getResource(VIEW_PATH));
            fxmlLoader.setController(new LuaChonNoiThatController());
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

    private void addCssToScence() {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }

    static public Node getNewRoot() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LuaChonNoiThatScene.class.getResource(VIEW_PATH));
        fxmlLoader.setController(new LuaChonNoiThatController());
        Parent root = fxmlLoader.load();
        return root;
    }
}
