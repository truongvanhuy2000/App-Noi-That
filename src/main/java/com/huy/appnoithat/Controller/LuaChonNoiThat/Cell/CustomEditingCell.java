package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Utils.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class CustomEditingCell<X> extends TreeTableCell<BangNoiThat, X> {
    private final Logger LOGGER = LogManager.getLogger(this);
    protected final TreeTableView<BangNoiThat> TableNoiThat;
    protected final StringConverter<X> converter;

    protected TextField textField;

    /**
     * Starts the editing process for this component. Overrides the superclass method
     * to initiate editing only if the component is not empty. Invokes the superclass's
     * startEdit method, creates a new text field, clears the current content, sets the
     * text field as the graphic content, and selects all text within the field for easy
     * modification.
     */
    @Override
    public void startEdit() {
        if (!TableUtils.isEditable(TableNoiThat)) {
            return;
        }
        super.startEdit();
        createTextField();
        setText(null);
        setGraphic(textField);
        textField.selectAll();
        textField.requestFocus();
    }

    /**
     * Cancels the editing process for this component. Overrides the superclass method
     * to revert the component's state to its original value. Invokes the superclass's
     * cancelEdit method, sets the text to the original item value, and removes the graphic content.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
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
    public void updateItem(X item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
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
        textField = new TextField();
        textField.setText(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnAction((e) -> {
            super.commitEdit(converter.fromString(textField.getText()));
            textField.clear();
        });
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                LOGGER.debug("Unfocused cell");
                String textFieldValue = textField.getText();
                if (StringUtils.isBlank(textFieldValue)) {
                    LOGGER.debug("Input text is blank");
                    return;
                }
                X convertedData = converter.fromString(textFieldValue);
                LOGGER.debug("convertedData {}, getItem() {}", convertedData, getItem());
                if (convertedData.equals(getItem())) {
                    LOGGER.debug("Final value not change, won't fire any event");
                    return;
                }
                textField.clear();
                commitWhenOutFocus(convertedData);
            }
        });
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
                t.consume();
            }
        });
    }

    private void commitWhenOutFocus(X value) {
        TreeTableView<BangNoiThat> treeTableView = this.getTreeTableView();
        TreeTablePosition<BangNoiThat, X> position = new TreeTablePosition<>(treeTableView, this.getIndex(), super.getTableColumn());
        LOGGER.debug("==FIRE CELL EDIT WHEN OUT OF FOCUS EVENT==");
        TreeTableColumn.CellEditEvent<BangNoiThat, X> editEvent = new TreeTableColumn.CellEditEvent<>(
                treeTableView, position, TreeTableColumn.editCommitEvent(), value);
        Event.fireEvent(this.getTableColumn(), editEvent);
    }

    protected String getString() {
        return getItem() == null ? "" : converter.toString(getItem());
    }
}
