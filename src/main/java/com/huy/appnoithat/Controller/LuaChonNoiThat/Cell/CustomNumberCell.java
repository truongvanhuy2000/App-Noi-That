package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.StringConverter;

public class CustomNumberCell<T> extends TextFieldTreeTableCell<BangNoiThat, T> {
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private boolean isKichThuocCollum = false;
    /**
     * Constructs a CustomNumberCell with the given StringConverter and TreeTableView.
     *
     * @param var0        The StringConverter object for converting between strings and the generic type T.
     * @param TableNoiThat The TreeTableView associated with this cell.
     */
    public CustomNumberCell(StringConverter<T> var0, TreeTableView<BangNoiThat> TableNoiThat, boolean isKichThuocCollum) {
        super(var0);
        this.TableNoiThat = TableNoiThat;
        this.isKichThuocCollum = isKichThuocCollum;
    }

    /**
     * Initiates the editing process for this cell if the associated TreeTableView is editable.
     * Overrides the superclass method to ensure editing is allowed before invoking it.
     */
    @Override
    public void startEdit() {
        if (!TableUtils.isEditable(TableNoiThat)) {
            return;
        }
        super.startEdit();
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
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        if (item.equals(0.0) || item.equals(0) || item.equals(0F) || item.equals(0L)) {
            setText("");
            return;
        }
        if (isKichThuocCollum) {
            String text = item.toString();
            if (text.contains(".")) {
                text = text.split("\\.")[0];
                setText(text);
            }
        }
    }
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        if (isKichThuocCollum) {
            String text = getItem().toString();
            if (text.contains(".")) {
                text = text.split("\\.")[0];
                setText(text);
            }
        }
    }
}
