package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class DeepCopyCommand implements Command {
    private final Logger LOGGER = LogManager.getLogger(this);

    private final TreeTableView<BangNoiThat> TableNoiThat;
    private TreeItem<BangNoiThat> duplicatedEntries;
    private TreeItem<BangNoiThat> currentSelectedItem;

    @Override
    public void execute() {
        currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        if (currentSelectedItem == null) {
            return;
        }
        duplicatedEntries = deepCopy(currentSelectedItem);
        if (duplicatedEntries == null) {
            return;
        }
        TreeItem<BangNoiThat> newSibling = TableUtils.createNewSibling(currentSelectedItem, duplicatedEntries);
        if (newSibling != null) {
            TableUtils.selectSingleItem(TableNoiThat, newSibling);
        }
        TableUtils.reArrangeList(TableNoiThat);
        TableCalculationUtils.recalculateAllTongTien(TableNoiThat);
    }

    @Override
    public void undo() {
        if (currentSelectedItem != null) {
            LOGGER.info("undo DeepCopyCommand");
            currentSelectedItem.getParent().getChildren().remove(duplicatedEntries);
            TableUtils.reArrangeList(TableNoiThat);
            TableCalculationUtils.recalculateAllTongTien(TableNoiThat);
        }
    }

    private TreeItem<BangNoiThat> deepCopy(TreeItem<BangNoiThat> item) {
        if (item == null) {
            return null;
        }
        TreeItem<BangNoiThat> newItem = TableUtils.createNewItem(item.getValue());
        for (TreeItem<BangNoiThat> child : item.getChildren()) {
            newItem.getChildren().add(deepCopy(child));
        }
        return newItem;
    }

}
