package com.huy.appnoithat.Controller.Common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SaveBeforeCloseAlert extends Alert {
    public static final ButtonType buttonTypeSave = new ButtonType("Lưu");
    public static final ButtonType buttonTypeNotSave = new ButtonType("Không Lưu");
    public static final ButtonType buttonTypeCancel = new ButtonType("Hủy");

    public SaveBeforeCloseAlert() {
        super(AlertType.CONFIRMATION, "", buttonTypeSave, buttonTypeNotSave, buttonTypeCancel );
        setTitle("AppNoiThat");
        setHeaderText("Bạn có muốn lưu lại các thay đổi không?\n");
        setContentText("Nếu chọn \"Không Lưu\", bản lưu tạm thời sẽ được tạo ra.");
    }
}
