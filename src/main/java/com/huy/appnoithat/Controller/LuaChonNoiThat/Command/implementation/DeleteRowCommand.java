package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class DeleteRowCommand implements Command {
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private final Map<TreeItem<BangNoiThat>, List<TreeItem<BangNoiThat>>> backupMap = new HashMap<>();

    @Override
    public void execute() {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()) {
            return;
        }
        ObservableList<TreeItem<BangNoiThat>> listItem = TableNoiThat.getSelectionModel().getSelectedItems();
        for (int i = listItem.size() - 1; i >= 0; i--) {
            TreeItem<BangNoiThat> item = listItem.get(i);
            if (item == null || item.getParent() == null) {
                continue;
            }
            createBackup(item);
            item.getParent().getChildren().remove(item);
        }
        TableNoiThat.getSelectionModel().clearSelection();
        TableUtils.reArrangeList(TableNoiThat);
        TableCalculationUtils.recalculateAllTongTien(TableNoiThat);
    }

    @Override
    public void undo() {
        backupMap.forEach(this::restore);
        TableUtils.reArrangeList(TableNoiThat);
        TableCalculationUtils.recalculateAllTongTien(TableNoiThat);
    }

    private void createBackup(TreeItem<BangNoiThat> removedItem) {
        TreeItem<BangNoiThat> parent = removedItem.getParent();
        if (!backupMap.containsKey(parent)) {
            backupMap.put(parent, new ArrayList<>());
            backupMap.get(parent).addAll(parent.getChildren());
        }
    }

    private void restore(TreeItem<BangNoiThat> parent, List<TreeItem<BangNoiThat>> removedItemList) {
        parent.getChildren().clear();
        parent.getChildren().addAll(removedItemList);
    }
}
