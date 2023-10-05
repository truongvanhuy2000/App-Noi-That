package com.huy.appnoithat.Scene;

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
    private static final String viewPath = "view/LuaChonNoiThatLayout.fxml";
    private Scene scene;
    private Parent root;
    private static LuaChonNoiThatScene single_instance = null;

    public LuaChonNoiThatScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LuaChonNoiThatScene.class.getResource(viewPath));
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
        String cssPath = "css/LuaChonNoiThatLayout.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }

    static public Node getNewRoot() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LuaChonNoiThatScene.class.getResource(viewPath));
        fxmlLoader.setController(new LuaChonNoiThatController());
        Parent root = fxmlLoader.load();
        return root;
    }
}
