package com.huy.appnoithat.Scene;

import com.huy.appnoithat.HelloApplication;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class StageFactory {
    public static Stage closeAndCreateNewMaximizedStage(Stage currentStage, Scene nextScene) {
        currentStage.close();
        Stage stage = new Stage();
        stage.setScene(nextScene);
        stage.setMaximized(true);
        setUpStage(stage);
        return stage;
    }
    public static Stage closeAndCreateNewUnresizeableStage(Stage currentStage, Scene nextScene) {
        currentStage.close();
        Stage stage = new Stage();
        stage.setScene(nextScene);
        stage.setResizable(false);
        setUpStage(stage);;
        return stage;
    }
    public static Stage CreateNewUnresizeableStage(Scene nextScene) {
        Stage stage = new Stage();
        stage.setScene(nextScene);
        stage.setResizable(false);
        setUpStage(stage);
        return stage;
    }
    public static Stage CreateNewMaximizedStage(Scene nextScene) {
        Stage stage = new Stage();
        stage.setScene(nextScene);
        stage.setMaximized(true);
        setUpStage(stage);
        return stage;
    }
    private static void setUpStage(Stage stage) {
        stage.setTitle("App Noi That");
        stage.getIcons().add(new Image(Objects.requireNonNull(
                HelloApplication.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/logoapp.jpg"))));
        stage.requestFocus();
        stage.setAlwaysOnTop(true);
        stage.toFront();
        stage.show();
    }
}
