package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.TableUtils;
import javafx.scene.control.TreeTableCell;
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
    public void startEdit(){
        if (!TableUtils.isEditable(TableNoiThat)){
            System.out.println("we suppose to reach here");
            return;
        }
        super.startEdit();
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
