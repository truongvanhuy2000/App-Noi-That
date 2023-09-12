package com.huy.appnoithat.Scene;

import com.huy.appnoithat.Controller.NewTabController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;
@Getter
public class NewTabScene {
    private static final String viewPath = "view/TabLayout.fxml";
    private Scene scene;
    private Parent root;
    private static NewTabScene single_instance = null;
    private NewTabController newTabController;
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
    // Create an object of this class, call this function
    public static synchronized NewTabScene getInstance() {
        if (single_instance == null)
            single_instance = new NewTabScene();
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
        String cssPath = "css/TabLayout.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
    }
}
