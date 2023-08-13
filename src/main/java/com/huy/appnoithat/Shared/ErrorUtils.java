package com.huy.appnoithat.Shared;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ErrorUtils {
    public static void throwErrorSignal(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }
}
