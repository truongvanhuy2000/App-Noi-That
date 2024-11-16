package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Utils.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddNewItemIfEmptyCommand implements Command {
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private TreeItem<BangNoiThat> newlyCreatedItem;

    @Override
    public void execute() {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()) {
            addNewItemIfEmpty();
        }
    }

    @Override
    public void undo() {
        if (newlyCreatedItem != null) {
            newlyCreatedItem.getParent().getChildren().remove(newlyCreatedItem);
        }
    }

    private void addNewItemIfEmpty() {
        if (TableNoiThat.getRoot().getChildren().isEmpty()) {
            newlyCreatedItem = TableUtils.createNewItem(ItemType.AlPHA, "A");
            TableNoiThat.getRoot().getChildren().add(newlyCreatedItem);
            TreeItem<BangNoiThat> newRomanStt = TableUtils.createNewItem(ItemType.ROMAN, "I");
            automaticallyAddNewNumericStt(newRomanStt, 5);
            newlyCreatedItem.getChildren().add(newRomanStt);
        }
    }

    private void automaticallyAddNewNumericStt(TreeItem<BangNoiThat> parent, int count) {
        for (int i = 0; i < count; i++) {
            TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem(ItemType.NUMERIC, String.valueOf(i + 1));
            parent.getChildren().add(tempNewItem);
        }
    }
}
