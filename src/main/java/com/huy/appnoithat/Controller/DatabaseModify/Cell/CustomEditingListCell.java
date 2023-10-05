package com.huy.appnoithat.Controller.DatabaseModify.Cell;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Entity.Common.CommonItemInterface;
import com.huy.appnoithat.Enums.Action;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class CustomEditingListCell<T extends CommonItemInterface> extends ListCell<T> {
    private TextArea textArea;
    private VBox vBox;
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
                setGraphic(vBox);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
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
        setText(getItem().getName());
        setGraphic(null);
    }

    private void createTextArea() {
        if (textArea != null) {
            return;
        }
        textArea = new TextArea(getString());
        textArea.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textArea.setOnKeyPressed((event) -> {
            T item = getItem();
            if (item == null) {
                return;
            }
            item.setName(textArea.getText());
            if (KeyboardUtils.isRightKeyCombo(Action.SAVE, event)) {
                commitEdit(item);
                updateItem(item, false);
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
        return getItem() == null ? "" : getItem().getName();
    }
}
