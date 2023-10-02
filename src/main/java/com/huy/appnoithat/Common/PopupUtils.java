package com.huy.appnoithat.Common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

import java.io.File;

public class PopupUtils {
    public static void throwErrorSignal(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    public static void throwSuccessSignal(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.show();
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
}
