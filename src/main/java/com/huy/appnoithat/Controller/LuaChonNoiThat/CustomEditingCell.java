package com.huy.appnoithat.Controller.LuaChonNoiThat;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.util.StringConverter;

public class CustomEditingCell<BangNoiThat> extends TreeTableCell<BangNoiThat, String> {
    private TextField textField;
    public CustomEditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
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
//        System.out.println("update editing cell");
        super.updateItem(item, empty);
        if (empty) {
            setText(item);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                    setGraphic(null);
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {

        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnAction((e) -> commitEdit(textField.getText()));
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                System.out.println("Commiting " + textField.getText());
                commitEdit(textField.getText());
            }
        });
    }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
}
