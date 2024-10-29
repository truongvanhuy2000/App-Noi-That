package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Utils.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContinuousAddForAlphaSttCommand implements Command {
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private TreeItem<BangNoiThat> newlyCreatedItem;

    @Override
    public void execute() {
        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        if (currentSelectedItem == null) {
            return;
        }
        handleContinuousAddForAlphaStt(currentSelectedItem);
    }

    @Override
    public void undo() {
        if (newlyCreatedItem != null) {
            newlyCreatedItem.getParent().getChildren().remove(newlyCreatedItem);
            TableUtils.reArrangeList(TableNoiThat);
        }
    }

    private void handleContinuousAddForAlphaStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (isReachedLimit(currentSelectedItem, 26)) {
            return;
        }
        if (currentSelectedItem.getChildren().isEmpty()) {
            newlyCreatedItem = continuousLineAddForAlphaStt(currentSelectedItem);
            automaticallyAddNewNumericStt(newlyCreatedItem, 5);
        } else {
            newlyCreatedItem = TableUtils.createNewSibling(currentSelectedItem).orElseThrow();
            TableUtils.selectSingleItem(TableNoiThat, newlyCreatedItem);
            TreeItem<BangNoiThat> newRomanStt = TableUtils.createNewItem(ItemType.ROMAN, "I");
            automaticallyAddNewNumericStt(newRomanStt, 5);
            newlyCreatedItem.getChildren().add(newRomanStt);
        }
    }


    public void automaticallyAddNewNumericStt(TreeItem<BangNoiThat> parent, int count) {
        if (parent == null) {
            return;
        }
        for (int i = 0; i < count; i++) {
            TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem(ItemType.NUMERIC, String.valueOf(i + 1));
            parent.getChildren().add(tempNewItem);
        }
    }


    /**
     * Adds a new child item with the next alphabetical STT ("I", "II", etc.) under the given parent item.
     * If the parent item is null, the method does nothing.
     *
     * @param currentSelectedItem The parent item to which a new child item is added.
     */
    private TreeItem<BangNoiThat> continuousLineAddForAlphaStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (currentSelectedItem == null) {
            return null;
        }
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem(ItemType.ROMAN, "I");
        currentSelectedItem.getChildren().add(tempNewItem);
//        selectNewItem(tempNewItem);
        return tempNewItem;
    }

    /**
     * Finds the next sequential STT (Serial Number) based on the input STT. Supports Roman, alphabetical, and numeric STTs.
     * If the input STT is null, empty, or not recognized, returns an empty string.
     *
     * @param stt The input STT (Serial Number).
     * @return The next sequential STT.
     */
    public boolean isReachedLimit(TreeItem<BangNoiThat> root, int limit) {
        if (root == null) {
            return false;
        }
        if (root.getParent().getChildren().size() >= limit) {
            PopupUtils.throwErrorNotification("Đã đạt giới hạn số lượng " + limit + " mục");
            return true;
        }
        return false;
    }
}
