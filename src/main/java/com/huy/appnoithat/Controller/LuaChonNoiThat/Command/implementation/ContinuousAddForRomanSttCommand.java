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
public class ContinuousAddForRomanSttCommand implements Command {
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private TreeItem<BangNoiThat> newlyCreatedItem;

    @Override
    public void execute() {
        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        if (currentSelectedItem == null) {
            return;
        }
        handleContinuousAddForRomanStt(currentSelectedItem);
    }

    @Override
    public void undo() {
        if (newlyCreatedItem != null) {
            newlyCreatedItem.getParent().getChildren().remove(newlyCreatedItem);
        }
    }

    /**
     * Handles the continuous addition of items in the Roman numeral sequence format.
     * If the current item has no children, a new child is added. Otherwise, a new sibling is created.
     *
     * @param currentSelectedItem The currently selected item in the TreeTableView.
     */
    private void handleContinuousAddForRomanStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (isReachedLimit(currentSelectedItem, 50)) {
            return;
        }
        if (currentSelectedItem.getChildren().isEmpty()) {
            newlyCreatedItem = continuousLineAddForRomanStt(currentSelectedItem);
            return;
        }
        newlyCreatedItem = TableUtils.createNewSibling(currentSelectedItem).orElseThrow();
        TableUtils.selectSingleItem(TableNoiThat, newlyCreatedItem);
        automaticallyAddNewNumericStt(newlyCreatedItem, 5);
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
     * Adds a new child item with the next numeric STT ("1", "2", etc.) under the given parent item.
     * If the parent item is null, the method does nothing.
     *
     * @param currentSelectedItem The parent item to which a new child item is added.
     */
    private TreeItem<BangNoiThat> continuousLineAddForRomanStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (currentSelectedItem == null) {
            return null;
        }
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem(ItemType.NUMERIC, "1");
        currentSelectedItem.getChildren().add(tempNewItem);
        selectNewItem(tempNewItem);
        return tempNewItem;
    }


    /**
     * Selects the provided TreeItem in the TreeTableView and clears any existing selections.
     * If the provided item is null, the method does nothing.
     *
     * @param tempNewItem The TreeItem to be selected in the TreeTableView.
     */
    private void selectNewItem(TreeItem<BangNoiThat> tempNewItem) {
        if (tempNewItem == null) {
            return;
        }
        TableNoiThat.getSelectionModel().clearSelection();
        TableNoiThat.getSelectionModel().select(tempNewItem);
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
