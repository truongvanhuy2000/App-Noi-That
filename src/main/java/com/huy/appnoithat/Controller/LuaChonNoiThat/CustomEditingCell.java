package com.huy.appnoithat.Controller.LuaChonNoiThat;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

public class CustomEditingCell<T> extends TableCell<BangNoiThat, T> {
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
//                textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

//            setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
    }

    private void createTextField() {
//            textField = new TextField(getString());
//            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
//            textField.setOnAction((e) -> commitEdit(textField.getText()));
//            textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//                if (!newValue) {
//                    System.out.println("Commiting " + textField.getText());
//                    commitEdit(textField.getText());
//                }
//            });
    }

//        private String getString() {
//            return getItem() == null ? "" : getItem();
//        }
}
