package com.huy.appnoithat.Shared;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
}
