package com.huy.appnoithat.Scene;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.HelloApplication;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Objects;

public class StageFactory {
    public static Stage createNewMaximizedMainStage(Stage currentStage, Scene nextScene, boolean confirmWhenClose) {
        currentStage.setScene(nextScene);
        currentStage.setResizable(true);
        Platform.runLater(() -> {
            currentStage.setMaximized(true);
        });
        setUpStage(currentStage, confirmWhenClose);
        return currentStage;
    }
    public static Stage createNewUnResizeableMainStage(Stage currentStage, Scene nextScene, boolean confirmWhenClose) {
        currentStage.setScene(nextScene);
        currentStage.setMaximized(false);
        currentStage.setResizable(false);
        setUpStage(currentStage, confirmWhenClose);
        return currentStage;
    }
    public static Stage CreateNewUnresizeableStage(Scene nextScene, boolean confirmWhenClose) {
        Stage stage = new Stage();
        stage.setScene(nextScene);
        stage.setResizable(false);
        setUpStage(stage, confirmWhenClose);
        return stage;
    }
    public static Stage CreateNewMaximizedStage(Scene nextScene, boolean confirmWhenClose) {
        Stage stage = new Stage();
        stage.setScene(nextScene);
        stage.setMaximized(true);
        setUpStage(stage, confirmWhenClose);
        return stage;
    }
    private static void setUpStage(Stage stage, boolean confirmWhenClose) {
        stage.setTitle("App Noi That");
        stage.getIcons().add(new Image(Objects.requireNonNull(
                HelloApplication.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/logoapp.jpg"))));
        stage.requestFocus();
        stage.toFront();
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            if (confirmWhenClose && !PopupUtils.showCloseWindowConfirmation()) {
                return;
            }
            stage.close();
            Platform.runLater(() -> {
                if (Window.getWindows().isEmpty()) {
                    System.exit(0);
                }
            });
        });
    }
}
