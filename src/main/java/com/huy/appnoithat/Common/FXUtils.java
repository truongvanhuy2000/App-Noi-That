package com.huy.appnoithat.Common;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class FXUtils {
    public static void closeAll() {
        List<Window> windows = new ArrayList<>(Window.getWindows());
        for (Window window : windows) {
            if (window != null && window.isShowing()) {
                window.hide();
            }
        }
    }
    public static void showLoading(StackPane loadingPane, String text) {
        loadingPane.setVisible(true);
        loadingPane.setDisable(false);
        VBox vbox = new VBox();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.setMinHeight(100);
        progressIndicator.setMinWidth(100);
        Label textField = new Label(text);
        textField.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
        vbox.getChildren().addAll(progressIndicator, textField);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        loadingPane.getChildren().addAll(vbox);
        loadingPane.toFront();
        loadingPane.setStyle("-fx-background-color: rgba(255,255,255,0.65)");
    }
    public static void hideLoading(StackPane loadingPane) {
        loadingPane.setVisible(false);
        loadingPane.setDisable(true);
        loadingPane.getChildren().clear();
        loadingPane.toBack();
    }

    public static Node getCurrentRootNode() {
        Window popupWindow = org.controlsfx.tools.Utils.getWindow(null);
        if (popupWindow == null) {
            return null;
        }
        return popupWindow.getScene().getRoot();
    }

    public static void loadingMouse() {
        if (Platform.isFxApplicationThread()) {
            Objects.requireNonNull(FXUtils.getCurrentRootNode()).getScene().setCursor(Cursor.WAIT);
        }
    }
    public static void normalMouse() {
        if (Platform.isFxApplicationThread()) {
            Objects.requireNonNull(FXUtils.getCurrentRootNode()).getScene().setCursor(Cursor.DEFAULT);
        }
    }
}
