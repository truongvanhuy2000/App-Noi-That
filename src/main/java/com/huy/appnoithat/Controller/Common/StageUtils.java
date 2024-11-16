package com.huy.appnoithat.Controller.Common;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.NonNull;

public class StageUtils {
    public static void closeStage(@NonNull Stage stage) {
        stage.close();
        Platform.runLater(() -> {
            if (Window.getWindows().isEmpty()) {
                System.exit(0);
            }
        });
    }
}
