package com.huy.appnoithat.Scene;

import com.google.inject.Inject;
import com.huy.appnoithat.Controller.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

@Getter
public class HomeScene implements GenericScene{
    final static Logger LOGGER = LogManager.getLogger(HomeScene.class);
    private static final String VIEW_PATH = "view/HomeRemakeLayout.fxml";
    private static final String CSS_PATH = "css/HomeLayout.css";
    private Scene scene;
    private Parent root;
    private final HomeController homeController;

    public HomeScene(HomeController homeController) {
        this.homeController = homeController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeScene.class.getResource(VIEW_PATH));
            fxmlLoader.setController(homeController);
            root = fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.error("Error loading HomeScene: " + e.getMessage());
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        addCssToScene();
    }

    private void addCssToScene() {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH)).toExternalForm());
    }
}
