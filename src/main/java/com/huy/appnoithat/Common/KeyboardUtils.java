package com.huy.appnoithat.Common;

import com.huy.appnoithat.Enums.Action;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class KeyboardUtils {
    public static boolean isRightKeyCombo(Action action, KeyEvent keyEvent) {
        switch (action) {
            case SAVE -> {
                return keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.S);
            }
            case DELETE -> {
                return keyEvent.getCode().equals(KeyCode.DELETE);
            }
            case LOGIN -> {
                return keyEvent.getCode().equals(KeyCode.ENTER);
            }
            case EXIT, CLEAR_SELECTION -> {
                return keyEvent.getCode().equals(KeyCode.ESCAPE);
            }
            case ADD_NEW_ROW -> {
                return keyEvent.getCode().equals(KeyCode.ADD);
            }
            case NEXT_SCREEN -> {
                return keyEvent.getCode().equals(KeyCode.RIGHT);
            }
        }
        return false;
    }
}
