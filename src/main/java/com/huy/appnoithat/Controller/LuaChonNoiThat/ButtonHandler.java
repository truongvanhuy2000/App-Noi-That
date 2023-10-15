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
    public ButtonHandler(LuaChonNoiThatController luaChonNoiThatController) {
        this.TableNoiThat = luaChonNoiThatController.getTableNoiThat();
        this.luaChonNoiThatController = luaChonNoiThatController;
    }
    public void continuousLineAdd(ActionEvent event) {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()) {
            if (TableNoiThat.getRoot().getChildren().isEmpty()) {
                TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("A");
                TableNoiThat.getRoot().getChildren().add(tempNewItem);
            }
            return;
        }
        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        if (currentSelectedItem == null) {
            return;
        }
        String currentItemStt = currentSelectedItem.getValue().getSTT().getValue();
        if (Utils.RomanNumber.isRoman(currentItemStt)) {
            handleContinuousAddForRomanStt(currentSelectedItem);
            TableUtils.reArrangeList(TableNoiThat);
            return;
        }
        if (Utils.isAlpha(currentItemStt)) {
            handleContinuousAddForAlphaStt(currentSelectedItem);
            TableUtils.reArrangeList(TableNoiThat);
            return;
        }
        if (Utils.isNumeric(currentItemStt)) {
            handleContinuousAddForNumericStt();
            TableUtils.reArrangeList(TableNoiThat);
        }
    }

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

    private void handleContinuousAddForNumericStt() {
        for (int i = 0; i < 5; i++) {
            TreeItem<BangNoiThat> item = TableNoiThat.getSelectionModel().getSelectedItem();
            continuousLineAddForNumericStt(item);
        }
    }

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

    private void continuousLineAddForAlphaStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (currentSelectedItem == null) {
            return;
        }
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("I");
        currentSelectedItem.getChildren().add(tempNewItem);
        selectNewItem(tempNewItem);
    }

    private void continuousLineAddForRomanStt(TreeItem<BangNoiThat> currentSelectedItem) {
        if (currentSelectedItem == null) {
            return;
        }
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("1");
        currentSelectedItem.getChildren().add(tempNewItem);
        selectNewItem(tempNewItem);
    }

    private void selectNewItem(TreeItem<BangNoiThat> tempNewItem) {
        if (tempNewItem == null) {
            return;
        }
        TableNoiThat.getSelectionModel().clearSelection();
        TableNoiThat.getSelectionModel().select(tempNewItem);
    }

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
