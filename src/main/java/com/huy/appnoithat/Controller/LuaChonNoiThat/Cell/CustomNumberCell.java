package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.BangNoiThat;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.StringConverter;

public class CustomNumberCell<T> extends TextFieldTreeTableCell<BangNoiThat, T> {
    public CustomNumberCell(StringConverter<T> var0) {
        super(var0);
    }
    @Override
    public void updateItem(T item, boolean empty) {
        if (item == null || empty){
            return;
        }
        super.updateItem(item, empty);
//        System.out.println(item);
        if (item.equals(0.0) || item.equals(0) || item.equals(0F) || item.equals(0L)){
            setText("");
        }
    }
}
