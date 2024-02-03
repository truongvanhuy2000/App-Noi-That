package com.huy.appnoithat.Controller.DatabaseModify.Cell;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.DataModel.Entity.CommonItemInterface;
import com.huy.appnoithat.DataModel.Enums.Action;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class CustomEditingListCell<T extends CommonItemInterface> extends ListCell<T> {
    private TextArea textArea;
    private VBox vBox;

    /**
     * Customizes the appearance of a cell in a TableView.
     *
     * @param item  The item to be displayed in the cell.
     * @param empty Indicates whether the cell is empty or not.
     */
    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(getString());
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textArea != null) {
                    textArea.setText(getValue());
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


    /**
     * Called when the cell enters editing mode.
     * Creates a TextArea and VBox to allow editing of the cell's content.
     */
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

    /**
     * Cancels the editing process, displaying the original item content.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getName());
        setGraphic(null);
    }


    /**
     * Creates a TextArea for editing the cell's content.
     */
    private void createTextArea() {
        if (textArea != null) {
            return;
        }
        textArea = new TextArea(getValue());
        textArea.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textArea.setOnKeyPressed((key) -> {
            if (KeyboardUtils.isRightKeyCombo(Action.NEXT_LINE, key)) {
                int currentTextPos = textArea.getCaretPosition();
                if (currentTextPos > textArea.getText().length()) {
                    currentTextPos = textArea.getText().length();
                }
                textArea.setText(new StringBuilder(textArea.getText())
                        .insert(currentTextPos, System.lineSeparator()).toString());
                textArea.positionCaret(currentTextPos + 1);
                key.consume();
                return;
            }
            if (KeyboardUtils.isRightKeyCombo(Action.COMMIT, key)) {
                T item = getItem();
                if (item == null) {
                    return;
                }
                item.setName(textArea.getText().trim());
                commitEdit(item);
                updateItem(item, false);
                key.consume();
            }
        });
    }


    /**
     * Creates a VBox containing a label with save instructions and the TextArea for editing.
     */
    private void createVBox() {
        if (vBox != null) {
            return;
        }
        vBox = new VBox();
        vBox.getChildren().add(new Label("Nhấn Alt + Enter để xuống dòng"));
        vBox.getChildren().add(textArea);
    }

    /**
     * Retrieves the string representation of the cell's item.
     * @return The string representation of the item's name.
     */
    private String getString() {
        return getItem() == null ? "" : (getIndex() + 1 + ". " + getItem().getName());
    }
    private String getValue() {
        return getItem() == null ? "" : getItem().getName();
    }
}
