package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.StringConverter;

public class CustomNumberCell<T> extends TextFieldTreeTableCell<BangNoiThat, T> {
    private TreeTableView<BangNoiThat> TableNoiThat;

    public CustomNumberCell(StringConverter<T> var0, TreeTableView<BangNoiThat> TableNoiThat) {
        super(var0);
        this.TableNoiThat = TableNoiThat;
    }

    @Override
    public void startEdit() {
        if (!TableUtils.isEditable(TableNoiThat)) {
            return;
        }
        super.startEdit();
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
            return;
        }
        if (item.equals(0.0) || item.equals(0) || item.equals(0F) || item.equals(0L)) {
            setText("");
        }
    }
}
