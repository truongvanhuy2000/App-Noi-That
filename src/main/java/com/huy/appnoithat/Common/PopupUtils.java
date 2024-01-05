package com.huy.appnoithat.Common;

import com.huy.appnoithat.Scene.LoadingScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class PopupUtils {
    public static void throwErrorSignal(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    public static void throwSuccessSignal(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
    public static void throwSuccessNotification(String message) {
        Image image = new Image(Objects.requireNonNull(
                PopupUtils.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/yes.png")));
        Notifications notifications = createNotification("", message, 3,null, image);
        Platform.runLater(notifications::show);
    }
    public static void throwErrorNotification(String message) {
        Image image = new Image(Objects.requireNonNull(
                PopupUtils.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/notificationError.png")));
        Notifications notifications = createNotification("", message, 3, null, image);
        Platform.runLater(notifications::show);
    }
    private static Notifications createNotification(String title, String message, int durationInSec, Runnable action, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        return Notifications.create()
                .title(title)
                .text(message)
                .graphic(imageView)
                .hideAfter(Duration.seconds(durationInSec))
                .position(Pos.TOP_RIGHT)
                .onAction((event) -> {
                    if (action != null) {
                        action.run();
                    }
                });
    }
    public static void throwErrorNotification(String title, String message, Runnable action, int durationInSec) {
        Image image = new Image(Objects.requireNonNull(
                PopupUtils.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/notificationError.png")));
        Notifications notifications = createNotification(title, message, durationInSec, action, image);
        Platform.runLater(notifications::show);
    }
    public static void throwCriticalError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    public static File fileSaver() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose location To Save Report");
        return chooser.showSaveDialog(null);
    }
    public static File fileOpener() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose location To Open Report");
        return chooser.showOpenDialog(null);
    }
    public static String openDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
    public static boolean confirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }
    public static void showLoading(Stage stage) {
        LoadingScene loadingScene = LoadingScene.getInstance();
        stage.setScene(loadingScene.getScene());
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public static boolean showCloseWindowConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đóng cửa sổ");
        alert.setHeaderText("Bạn có chắc chắn muốn đóng cửa sổ?\nHãy lưu lại những gì bạn đang thực hiện");
        alert.setContentText("Nhấn OK để đóng cửa sổ");
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }
    public static boolean showCloseAppConfirmation() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Đóng ứng dụng");
        alert.setHeaderText("Bạn có chắc chắn muốn đóng ứng dụng?\nHãy lưu lại những gì bạn đang thực hiện");
        alert.setContentText("Nhấn OK để đóng ứng dụng");
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }
}
