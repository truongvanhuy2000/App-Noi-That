package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Enums.Action;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeTableCell;
import javafx.scene.layout.VBox;

public class CustomTextAreaCell extends TreeTableCell<BangNoiThat, String> {
    private TextArea textArea;
    private VBox vBox;

    public CustomTextAreaCell() {
    }

    @Override
    public void startEdit() {
        createTextArea();
        createVBox();
        if (!isEmpty()) {
            super.startEdit();
            setText(null);
            setGraphic(vBox);
            textArea.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(item);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textArea != null) {
                    textArea.setText(getString());
                    setGraphic(null);
                }
                setText(null);
                setGraphic(vBox);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextArea() {
        if (textArea != null) {
            return;
        }
        textArea = new TextArea(getString());
        textArea.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textArea.setOnKeyPressed((key) -> {
            if (KeyboardUtils.isRightKeyCombo(Action.SAVE, key)) {
                commitEdit(textArea.getText());
                updateItem(textArea.getText(), false);
            }
        });
    }

    private void createVBox() {
        if (vBox != null) {
            return;
        }
        vBox = new VBox();
        vBox.getChildren().add(new Label("Nhấn Ctrl + S để lưu"));
        vBox.getChildren().add(textArea);
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
