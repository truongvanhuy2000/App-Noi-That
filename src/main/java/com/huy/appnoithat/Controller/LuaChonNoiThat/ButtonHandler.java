package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Enums.FileType;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

public class ButtonHandler {
    private TreeTableView<BangNoiThat> TableNoiThat;
    private LuaChonNoiThatController luaChonNoiThatController;


    /**
     * Initializes the ButtonHandler with the specified LuaChonNoiThatController instance and sets the internal
     * TreeTableView and LuaChonNoiThatController references.
     *
     * @param luaChonNoiThatController The LuaChonNoiThatController instance to associate with this ButtonHandler.
     */
    public ButtonHandler(LuaChonNoiThatController luaChonNoiThatController) {
        this.TableNoiThat = luaChonNoiThatController.getTableNoiThat();
        this.luaChonNoiThatController = luaChonNoiThatController;
    }

    /**
     * Handles the continuous addition of items in the TreeTableView. Determines the appropriate STT (sequence number)
     * format of the selected item and adds new items accordingly (Roman, Alphabet, or Numeric sequence). After adding
     * items, it rearranges the TreeTableView list to maintain the proper order.
     *
     * @param event The ActionEvent triggering the continuous line addition operation.
     */
    public void continuousLineAdd(ActionEvent event) {
        // Check if any items are selected in the TreeTableView
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()) {
            // If no items are selected and the TreeTableView is empty, add a new item with STT "A"
            if (TableNoiThat.getRoot().getChildren().isEmpty()) {
                TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("A");
                TableNoiThat.getRoot().getChildren().add(tempNewItem);
            }
            return;
        }

        // Get the currently selected item from the TreeTableView
        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        if (currentSelectedItem == null) {
            return;
        }
        String currentItemStt = currentSelectedItem.getValue().getSTT().getValue();

        // Determine the format of the STT and add new items accordingly
        if (Utils.RomanNumber.isRoman(currentItemStt)) {
            handleContinuousAddForRomanStt(currentSelectedItem);
            TableUtils.reArrangeList(TableNoiThat);
            return;
        }
        // If the STT is in alphabetical format, add items following the alphabetical sequence
        if (Utils.isAlpha(currentItemStt)) {
            handleContinuousAddForAlphaStt(currentSelectedItem);
            TableUtils.reArrangeList(TableNoiThat);
            return;
        }
        // If the STT is in numeric format, add items following the numeric sequence
        if (Utils.isNumeric(currentItemStt)) {
            handleContinuousAddForNumericStt();
            TableUtils.reArrangeList(TableNoiThat);
        }
    }


    /**
     * Handles the continuous addition of items in the Roman numeral sequence format.
     * If the current item has no children, a new child is added. Otherwise, a new sibling is created.
     *
     * @param currentSelectedItem The currently selected item in the TreeTableView.
     */
    private void handleContinuousAddForRomanStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (isReachedLimit(currentSelectedItem)) {
            return;
        }
        if (currentSelectedItem.getChildren().isEmpty()) {
            continuousLineAddForRomanStt(currentSelectedItem);
        } else {
            createNewSibling(currentSelectedItem);
        }
    }


    /**
     * Handles the continuous addition of items in the alphabetical sequence format.
     * If the current item has no children, a new child is added. Otherwise, a new sibling is created.
     *
     * @param currentSelectedItem The currently selected item in the TreeTableView.
     */
    private void handleContinuousAddForAlphaStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (isReachedLimit(currentSelectedItem)) {
            return;
        }
        if (currentSelectedItem.getChildren().isEmpty()) {
            continuousLineAddForAlphaStt(currentSelectedItem);
        } else {
            createNewSibling(currentSelectedItem);
        }
    }

    /**
     * Handles the continuous addition of items in the numeric sequence format. Adds five new items to the TreeTableView.
     */
    private void handleContinuousAddForNumericStt() {
        for (int i = 0; i < 5; i++) {
            TreeItem<BangNoiThat> item = TableNoiThat.getSelectionModel().getSelectedItem();
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
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("1");
        if (currentSelectedItem.getValue() == null) {
            return;
        }
        String nextStt = findTheNextStt(currentSelectedItem.getValue().getSTT().getValue());
        tempNewItem.getValue().getSTT().setValue(nextStt);
        parent.getChildren().add(tempNewItem);
        selectNewItem(tempNewItem);
    }


    /**
     * Adds a new child item with the next alphabetical STT ("I", "II", etc.) under the given parent item.
     * If the parent item is null, the method does nothing.
     *
     * @param currentSelectedItem The parent item to which a new child item is added.
     */
    private void continuousLineAddForAlphaStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (currentSelectedItem == null) {
            return;
        }
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("I");
        currentSelectedItem.getChildren().add(tempNewItem);
        selectNewItem(tempNewItem);
    }


    /**
     * Adds a new child item with the next numeric STT ("1", "2", etc.) under the given parent item.
     * If the parent item is null, the method does nothing.
     *
     * @param currentSelectedItem The parent item to which a new child item is added.
     */
    private void continuousLineAddForRomanStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (currentSelectedItem == null) {
            return;
        }
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("1");
        currentSelectedItem.getChildren().add(tempNewItem);
        selectNewItem(tempNewItem);
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
    public static String findTheNextStt(String stt) {
        if (stt == null || stt.isEmpty()) {
            return "";
        }
        if (Utils.RomanNumber.isRoman(stt)) {
            int nextStt = Utils.RomanNumber.romanToInt(stt) + 1;
            return Utils.RomanNumber.toRoman(nextStt);
        }
        if (Utils.isAlpha(stt)) {
            char nextLetter = (char) (stt.charAt(0) + 1);
            return String.valueOf(nextLetter);
        }
        if (Utils.isNumeric(stt)) {
            return String.valueOf(Integer.parseInt(stt) + 1);
        }
        return "";
    }

    public boolean isReachedLimit(TreeItem<BangNoiThat> root) {
        if (root == null) {
            return false;
        }
        if (root.getChildren().size() >= 30) {
            PopupUtils.throwErrorSignal("Đã đạt giới hạn số lượng 30");
            return true;
        }
        return false;
    }


    /**
     * Checks if adding a new child item to the given root TreeItem would exceed the maximum allowed limit of 30 children.
     * If the limit is reached, displays an error message and returns true; otherwise, returns false.
     *
     * @param root The parent TreeItem to which a new child item is to be added.
     * @return True if the limit is reached, false otherwise.
     */
    private void createNewSibling(TreeItem<BangNoiThat> currentItem) {
        if (currentItem == null) {
            return;
        }
        TreeItem<BangNoiThat> newItem = TableUtils.createNewItem(findTheNextStt(currentItem.getValue().getSTT().getValue()));
        if (currentItem.getParent() == null) {
            return;
        }
        int currentPos = currentItem.getParent().getChildren().indexOf(currentItem);
        if (currentPos != -1) {
            currentItem.getParent().getChildren().add(currentPos + 1, newItem);
        }
        TableUtils.selectSingleItem(TableNoiThat, newItem);
    }



    /**
     * Creates a new TreeItem sibling with the next sequential STT and adds it after the given TreeItem in the parent.
     * If the current item is null or its parent is null, the method does nothing.
     *
     * @param currentItem The TreeItem after which the new sibling is added.
     */
    public void exportButtonHandler(ActionEvent event) {
        this.luaChonNoiThatController.exportFile(FileType.EXCEL);
    }
    /**
     * @param event This function will handle the event when user want to save the table
     */
    public void onSaveAction(ActionEvent event) {
        if (this.luaChonNoiThatController.getCurrentState() == State.NEW_FILE) {
            this.luaChonNoiThatController.saveAs();
        } else {
            this.luaChonNoiThatController.save();
        }
    }
}
