package com.huy.appnoithat.Controller.DatabaseModify.Cell;

import com.huy.appnoithat.Entity.Common.CommonItemInterface;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class CustomEditingListCell<T extends CommonItemInterface> extends ListCell<T> {
    private TextArea textArea;
    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(getString());
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

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextArea();
            setText(null);
            setGraphic(textArea);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getName());
        setGraphic(null);
    }

    private void createTextArea() {
        textArea = new TextArea(getString());
        textArea.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textArea.setOnKeyPressed((key) -> {
            T item = getItem();
            if (item == null) {
                return;
            }
            item.setName(textArea.getText());
            if (key.getCode().equals(KeyCode.TAB)) {
                commitEdit(item);
                updateItem(item, false);
            }
        });
    }
    private String getString() {
        return getItem() == null ? "" : getItem().getName();
    }
}
