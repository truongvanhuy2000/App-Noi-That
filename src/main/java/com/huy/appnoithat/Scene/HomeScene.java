package com.huy.appnoithat.Scene;

import com.huy.appnoithat.Controller.HomeController;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;
@Getter
public class HomeScene {
    final static Logger LOGGER = LogManager.getLogger(HomeScene.class);
    private static final String VIEW_PATH = "view/HomeRemakeLayout.fxml";
    private static final String CSS_PATH = "css/HomeLayout.css";
    private Scene scene;
    private Parent root;
    private final FXMLLoader fxmlLoader;
    private static HomeScene single_instance = null;
    public HomeScene() {
        try {
            fxmlLoader = new FXMLLoader(HomeScene.class.getResource(VIEW_PATH));
            root = fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.error("Error loading HomeScene: " + e.getMessage());
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScene();
    }

    // Create an object of this class, call this function
    public static synchronized HomeScene getInstance() {
        if (single_instance == null)
            single_instance = new HomeScene();
        return single_instance;
    }

    public void setRoot(Parent root) {
        this.root = root;
        scene.setRoot(this.root);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        scene.setRoot(this.root);
        addCssToScene();
    }

    private void addCssToScene(){
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }
    public HomeController getHomeController(){
        return fxmlLoader.getController();
    }
    public double getHeight() {
        return scene.getHeight();
    }
    public double getWidth() {
        return scene.getWidth();
    }
}
