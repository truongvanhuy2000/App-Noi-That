package com.huy.appnoithat.Common;

import com.huy.appnoithat.Controller.NewTab.Operation.ContentOperation;
import com.huy.appnoithat.DataModel.Enums.FileType;
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
import javafx.stage.Window;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class PopupUtils {
    final static Logger LOGGER = LogManager.getLogger(PopupUtils.class);
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
        Platform.runLater(() -> {
            Notifications notifications = createNotification("Thành công", message, 3, null, image);
            notifications.show();
        });
    }

    public static void throwSuccessNotification(String message, Runnable runnable) {
        Image image = new Image(Objects.requireNonNull(
                PopupUtils.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/yes.png")));
        Platform.runLater(() -> {
            Notifications notifications = createNotification("Thành công", message, 3, runnable, image);
            notifications.show();
        });
    }

    public static void throwErrorNotification(String message) {
        Image image = new Image(Objects.requireNonNull(
                PopupUtils.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/notificationError.png")));

        Platform.runLater(() -> {
            Notifications notifications = createNotification("Thất bại", message, 3, null, image);
            notifications.show();
        });
    }

    private static Notifications createNotification(String title, String message, int durationInSec, Runnable action, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        String css = Objects.requireNonNull(PopupUtils.class.getResource("/Notification.css")).toExternalForm();
        Window popupWindow = org.controlsfx.tools.Utils.getWindow(null);
        popupWindow.getScene().getStylesheets().add(css);
        return Notifications.create()
                .owner(popupWindow)
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
        Platform.runLater(() -> {
            Notifications notifications = createNotification(title, message, durationInSec, action, image);
            notifications.show();
        });
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

    public static File fileSaver(FileType fileType) {
        FileChooser chooser = new FileChooser();
        boolean isOverwrite;
        chooser.setTitle("Choose location To Save Report");
        File choosenFile = chooser.showSaveDialog(null);
        if (choosenFile == null) {
            return null;
        }
        File fullPathFile = concatFileExtension(choosenFile, fileType);
        if (fullPathFile.exists()) {
            isOverwrite = confirmationDialog("", "File đã tồn tại!!!", "Bạn có muốn ghi đè file này?");
            if (!isOverwrite) {
                throwErrorNotification("Vui lòng chọn tên khác");
                return fileSaver(fileType);
            } else {
                fullPathFile.delete();
            }
        }
        return fullPathFile;
    }

    private static File concatFileExtension(File selectedFile, FileType fileType) {
        File outputFile = selectedFile;
        if (fileType == FileType.PDF) {
            if (!selectedFile.getAbsolutePath().contains(FileType.PDF.extension)) {
                outputFile = new File(String.format("%s.%s", outputFile.getAbsolutePath(), FileType.PDF.extension));
            }
        } else if (fileType == FileType.EXCEL) {
            if (!selectedFile.getAbsolutePath().contains(FileType.EXCEL.extension)) {
                outputFile = new File(String.format("%s.%s", outputFile.getAbsolutePath(), FileType.EXCEL.extension));
            }
        } else if (fileType == FileType.NT) {
            if (!selectedFile.getAbsolutePath().contains(FileType.NT.extension)) {
                outputFile = new File(String.format("%s.%s", outputFile.getAbsolutePath(), FileType.NT.extension));
            }
        } else {
            LOGGER.error("Not supported file type");
            throw new IllegalArgumentException();
        }
        return outputFile;
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
