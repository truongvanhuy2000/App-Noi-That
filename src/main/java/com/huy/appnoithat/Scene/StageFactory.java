package com.huy.appnoithat.Scene;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.HelloApplication;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class StageFactory {
    public static Stage createNewMaximizedMainStage(Stage currentStage, GenericScene nextScene, boolean confirmWhenClose) {
        currentStage.setScene(nextScene.getScene());
        currentStage.setResizable(true);
        Platform.runLater(() -> {
            currentStage.setMaximized(true);
        });
        setUpStage(currentStage, confirmWhenClose);
        return currentStage;
    }
    public static Stage createNewUnResizeableMainStage(Stage currentStage, GenericScene nextScene, boolean confirmWhenClose) {
        currentStage.setScene(nextScene.getScene());
        currentStage.setMaximized(false);
        currentStage.setResizable(false);
        setUpStage(currentStage, confirmWhenClose);
        return currentStage;
    }
    public static Stage CreateNewUnresizeableStage(GenericScene nextScene, boolean confirmWhenClose) {
        Stage stage = new Stage();
        stage.setScene(nextScene.getScene());
        stage.setResizable(false);
        setUpStage(stage, confirmWhenClose);
        return stage;
    }
    public static Stage CreateNewMaximizedStage(GenericScene nextScene, boolean confirmWhenClose) {
        Stage stage = new Stage();
        stage.setScene(nextScene.getScene());
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
