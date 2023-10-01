package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

public class ButtonHandler {
    private TreeTableView<BangNoiThat> TableNoiThat;

    public ButtonHandler(LuaChonNoiThatController luaChonNoiThatController) {
        this.TableNoiThat = luaChonNoiThatController.getTableNoiThat();
    }

    public void addNewLine(ActionEvent event) {
//        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()){
        if (TableNoiThat.getRoot() == null) {
            return;
        }
        TableNoiThat.getRoot().getChildren().add(TableUtils.createNewItem("A"));
//        }
//        if (isReachedLimit(TableNoiThat.getRoot())){
//            ErrorUtils.throwErrorSignal("Đã đạt giới hạn số lượng 30");
//            return;
//        }
//        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
//        TreeItem<BangNoiThat> parent = currentSelectedItem.getParent();
//
//        int indexOfCurrentItem = parent.getChildren().indexOf(currentSelectedItem);
//        parent.getChildren().add(indexOfCurrentItem + 1, new TreeItem<>(new BangNoiThat(
//                "A", 0f, 0f, 0f, 0L,
//                "", "", "", 0L, 0f)));
//        parent.setExpanded(true);
    }

    //    @Deprecated
//    public void continuousLineAdd() {
//        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()){
//            return;
//        }
//        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
//        TreeItem<BangNoiThat> parent = currentSelectedItem.getParent();
//        String stt = parent.getChildren().get(parent.getChildren().size() - 1).getValue().getSTT().getValue();
//        String nextStt = findTheNextStt(stt);
//        if (isReachedLimit(parent)) {
//            PopupUtils.throwErrorSignal("Đã đạt giới hạn số lượng 30");
//            return;
//        }
//        parent.getChildren().add(new TreeItem<>(new BangNoiThat(
//                nextStt, 0f, 0f, 0f, 0L,
//                "", "", "", 0L, 0f)));
//    }
    public void continuousLineAdd(ActionEvent event) {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()) {
            return;
        }
        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        if (currentSelectedItem == null) {
            return;
        }
        String currentItemStt = currentSelectedItem.getValue().getSTT().getValue();
        if (Utils.RomanNumber.isRoman(currentItemStt)) {
            handleContinuousAddForRomanStt(currentSelectedItem);
            return;
        }
        if (Utils.isAlpha(currentItemStt)) {
            handleContinuousAddForAlphaStt(currentSelectedItem);
            return;
        }
        if (Utils.isNumeric(currentItemStt)) {
            handleContinuousAddForNumericStt();
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

    public void onDeleteLine(ActionEvent event) {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()) {
            return;
        }
        ObservableList<TreeItem<BangNoiThat>> selectedList = TableNoiThat.getSelectionModel().getSelectedItems();

        for (int index = 0; index < selectedList.size(); index++) {
            TreeItem<BangNoiThat> item = selectedList.get(index);
            if (item == null) {
                return;
            }
            if (item.getParent() != null) {
                item.getParent().getChildren().remove(item);
                selectedList.remove(item);
            }
            if (index >= selectedList.size()) {
                index = 0;
            }
        }
//        for (TreeItem<BangNoiThat> item : selectedList){
//            if (item == null) {
//                return;
//            }
//            if (item.getParent() != null) {
//                item.getParent().getChildren().remove(item);
//            }
//        }
//        selectedList.forEach(
//                item -> {
//                    if (item == null) {
//                        return;
//                    }
//                    if (item.getParent() != null) {
//                        item.getParent().getChildren().remove(item);
//                        selectedList.remove(item);
//                    }
//                }
//        );
        TableNoiThat.getSelectionModel().clearSelection();
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
}
