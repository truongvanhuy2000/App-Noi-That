package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.ItemTypeUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ContinuousLineAddCommand implements Command {
    private final TreeTableView<BangNoiThat> TableNoiThat;

    @Override
    public void execute() {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()) {
            addNewItemIfEmpty();
            return;
        }

        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        if (currentSelectedItem == null) {
            return;
        }
        switch (ItemTypeUtils.determineItemType(currentSelectedItem)) {
            case ROMAN -> handleContinuousAddForRomanStt(currentSelectedItem);
            case AlPHA -> handleContinuousAddForAlphaStt(currentSelectedItem);
            case NUMERIC -> handleContinuousAddForNumericStt();
        }
        TableUtils.reArrangeList(TableNoiThat);
    }

    @Override
    public void undo() {

    }

    private void addNewItemIfEmpty() {
        if (TableNoiThat.getRoot().getChildren().isEmpty()) {
            TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem(ItemType.AlPHA, "A");
            TableNoiThat.getRoot().getChildren().add(tempNewItem);
            TreeItem<BangNoiThat> newRomanStt = TableUtils.createNewItem(ItemType.ROMAN, "I");
            automaticallyAddNewNumericStt(newRomanStt, 5);
            tempNewItem.getChildren().add(newRomanStt);
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
            continuousLineAddForRomanStt(currentSelectedItem);
            return;
        }
        TreeItem<BangNoiThat> newSibling = TableUtils.createNewSibling(currentSelectedItem);
        if (newSibling != null) {
            TableUtils.selectSingleItem(TableNoiThat, newSibling);
            automaticallyAddNewNumericStt(newSibling, 5);
        }
    }


    /**
     * Handles the continuous addition of items in the alphabetical sequence format.
     * If the current item has no children, a new child is added. Otherwise, a new sibling is created.
     *
     * @param currentSelectedItem The currently selected item in the TreeTableView.
     */
    private void handleContinuousAddForAlphaStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (isReachedLimit(currentSelectedItem, 26)) {
            return;
        }
        if (currentSelectedItem.getChildren().isEmpty()) {
            TreeItem<BangNoiThat> newItem = continuousLineAddForAlphaStt(currentSelectedItem);
            automaticallyAddNewNumericStt(newItem, 5);
        } else {
            TreeItem<BangNoiThat> newItem = TableUtils.createNewSibling(currentSelectedItem);
            if (newItem != null) {
                TableUtils.selectSingleItem(TableNoiThat, newItem);
                TreeItem<BangNoiThat> newRomanStt = TableUtils.createNewItem(ItemType.ROMAN, "I");
                automaticallyAddNewNumericStt(newRomanStt, 5);
                newItem.getChildren().add(newRomanStt);
            }
        }
    }

    /**
     * Handles the continuous addition of items in the numeric sequence format. Adds five new items to the TreeTableView.
     */
    private void handleContinuousAddForNumericStt() {
        addNewNumericStt(5);
    }

    private void addNewNumericStt(int num) {
        for (int i = 0; i < num; i++) {
            TreeItem<BangNoiThat> item = TableNoiThat.getSelectionModel().getSelectedItem();
            if (item == null) {
                throw new NullPointerException("No item is selected");
            }
            continuousLineAddForNumericStt(item);
        }
    }

    /**
     * Performs the continuous addition of items in the numeric sequence format.
     * Adds a new item with the next available numeric STT to the parent of the given item.
     *
     * @param currentSelectedItem The currently selected item in the TreeTableView.
     */
    private void continuousLineAddForNumericStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (currentSelectedItem == null) {
            return;
        }
        TreeItem<BangNoiThat> parent = currentSelectedItem.getParent();

        if (currentSelectedItem.getValue() == null) {
            return;
        }
        Optional<String> nextStt = ItemTypeUtils.findTheNextStt(currentSelectedItem.getValue().getSTT().getValue());
        if (nextStt.isPresent()) {
            TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem(ItemType.NUMERIC, nextStt.get());
            parent.getChildren().add(tempNewItem);
            selectNewItem(tempNewItem);
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
