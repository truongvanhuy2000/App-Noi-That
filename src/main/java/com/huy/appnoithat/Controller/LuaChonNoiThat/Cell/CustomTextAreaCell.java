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


    /**
     * Initiates the editing process for this cell. If the TextArea and VBox are not
     * initialized, creates them. If the cell is not empty, invokes the superclass's
     * startEdit method, sets the graphic content to the VBox containing the TextArea,
     * clears the text, and selects all text within the TextArea for easy modification.
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
     * Cancels the editing process for this cell. Overrides the superclass method
     * to revert the cell's state to its original value. Invokes the superclass's
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
     * the cell's appearance. If the item is null or empty, displays no graphic content.
     * If the item is numeric and equals zero, sets the text to an empty string.
     *
     * @param item  The item to be displayed in the cell.
     * @param empty Indicates whether the cell should be displayed as empty.
     */
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


    /**
     * Creates a TextArea for editing the cell's content. If the TextArea is already
     * initialized, this method does nothing. Configures the TextArea's behavior,
     * such as handling commit actions and keyboard shortcuts.
     */
    private void createTextArea() {
        if (textArea != null) {
            return;
        }
        textArea = new TextArea(getString());
        textArea.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textArea.setOnKeyPressed((key) -> {
            if (KeyboardUtils.isRightKeyCombo(Action.NEXT_LINE, key)) {
                textArea.appendText(System.getProperty("line.separator"));
            }
            if (KeyboardUtils.isRightKeyCombo(Action.COMMIT, key)) {
                commitEdit(textArea.getText());
                updateItem(textArea.getText(), false);
                key.consume();
            }
        });

    }


    /**
     * Creates a VBox containing a label with instructions and the TextArea. If the VBox
     * is already initialized, this method does nothing.
     */
    private void createVBox() {
        if (vBox != null) {
            return;
        }
        vBox = new VBox();
        vBox.getChildren().add(new Label("Nhấn Alt + Enter để xuống dòng"));
        vBox.getChildren().add(textArea);
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
