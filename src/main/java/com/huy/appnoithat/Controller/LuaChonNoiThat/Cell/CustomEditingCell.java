package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.DataModel.Enums.Action;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;

public class CustomEditingCell<BangNoiThat> extends TreeTableCell<BangNoiThat, String> {
    private TextField textField;
    boolean isSttCell = false;
    public CustomEditingCell(boolean isSttCell) {
        this.isSttCell = isSttCell;
    }


    /**
     * Starts the editing process for this component. Overrides the superclass method
     * to initiate editing only if the component is not empty. Invokes the superclass's
     * startEdit method, creates a new text field, clears the current content, sets the
     * text field as the graphic content, and selects all text within the field for easy
     * modification.
     */
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

    /**
     * Cancels the editing process for this component. Overrides the superclass method
     * to revert the component's state to its original value. Invokes the superclass's
     * cancelEdit method, sets the text to the original item value, and removes the graphic content.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);
    }


    /**
     * Updates the item within this cell and manages its visual representation based on
     * the provided item and its emptiness. Overrides the superclass method to customize
     * the cell's appearance. If the cell is empty, displays the item text; if editing,
     * shows a text field for user input; otherwise, customizes the cell's style based on
     * the type of item (numeric, Roman numeral, or alphabetic) and its position in the table.
     *
     * @param item  The item to be displayed in the cell.
     * @param empty Indicates whether the cell should be displayed as empty.
     */
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
            setText(item);
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

    /**
     * Creates a text field for editing the cell's content. If the text field already
     * exists, this method does nothing. Configures the text field's behavior, such as
     * handling commit actions, focusing, and keyboard shortcuts.
     */
    private void createTextField() {
        if (textField != null) {
            return;
        }
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnAction((e) -> commitEdit(textField.getText()));
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                commitEdit(textField.getText());
            }
        });
        textField.setOnKeyPressed((key) -> {
            if (KeyboardUtils.isRightKeyCombo(Action.SAVE, key)) {
                commitEdit(textField.getText());
                updateItem(textField.getText(), false);
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
