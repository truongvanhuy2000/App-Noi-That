package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeTableCell;
import javafx.scene.input.KeyCode;

public class CustomTextAreaCell extends TreeTableCell<BangNoiThat, String> {
    private TextArea textArea;
    public CustomTextAreaCell() {
    }
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textArea);
//            textField.selectAll();
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
                setGraphic(textArea);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }
    private void createTextField() {
        textArea = new TextArea(getString());
        textArea.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textArea.setOnKeyPressed((key) -> {
            if (key.getCode().equals(KeyCode.TAB)) {
//                System.out.println("Commiting " + textArea.getText());
                commitEdit(textArea.getText());
                updateItem(textArea.getText(), false);
            }
        });
    }
    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
