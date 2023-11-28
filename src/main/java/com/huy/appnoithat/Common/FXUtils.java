package com.huy.appnoithat.Common;

import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.experimental.UtilityClass;

import java.util.List;
@UtilityClass
public class FXUtils {
    public static void closeAll() {
        List<Window> windows = Window.getWindows();
        for (int index = 0; index < windows.size(); index ++) {
            Window window = windows.get(index);
            if (window != null) {
                Stage stage = (Stage) window;
                stage.close();
            }
        }
    }
}
