package com.huy.appnoithat.Common;

import com.huy.appnoithat.DataModel.Enums.Action;
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
            case LOGIN, COMMIT -> {
                return keyEvent.getCode().equals(KeyCode.ENTER);
            }
            case EXIT, CLEAR_SELECTION -> {
                return keyEvent.getCode().equals(KeyCode.ESCAPE);
            }
            case ADD_NEW_ROW -> {
                return keyEvent.getCode().equals(KeyCode.ADD);
            }
            case NEXT_SCREEN -> {
                return keyEvent.getCode().equals(KeyCode.N);
            }
            case NEXT_LINE -> {
                return keyEvent.isAltDown() && keyEvent.getCode().equals(KeyCode.ENTER);
            }
            case UNDO -> {
                return keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.Z);
            }
        }
        return false;
    }
}
