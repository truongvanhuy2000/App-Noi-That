package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Shared.PopupUtils;
import com.huy.appnoithat.Shared.Utils;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

public class ButtonHandler {
    private TreeTableView<BangNoiThat> TableNoiThat;

    public ButtonHandler(TreeTableView<BangNoiThat> tableNoiThat) {
        this.TableNoiThat = tableNoiThat;
    }
    public void addNewLine(ActionEvent event) {
//        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()){
        TableNoiThat.getRoot().getChildren().add(TableUtils.createNewItem("A"));
        return;
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

//    public void continuousLineAdd(ActionEvent event) {
//        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()){
//            return;
//        }
//        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
//        TreeItem<BangNoiThat> parent = currentSelectedItem.getParent();
//        String stt = parent.getChildren().get(parent.getChildren().size() - 1).getValue().getSTT().getValue();
//        String nextStt = findTheNextStt(stt);
//        if (isReachedLimit(parent)) {
//            ErrorUtils.throwErrorSignal("Đã đạt giới hạn số lượng 30");
//            return;
//        }
//        parent.getChildren().add(new TreeItem<>(new BangNoiThat(
//                nextStt, 0f, 0f, 0f, 0L,
//                "", "", "", 0L, 0f)));
//    }
    public void continuousLineAdd(ActionEvent event){
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()){
            return;
        }
        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        String currentItemStt = currentSelectedItem.getValue().getSTT().getValue();
        if (Utils.RomanNumber.isRoman(currentItemStt)){
            if (!isReachedLimit(currentSelectedItem)) {
                continuousLineAddForRomanStt(currentSelectedItem);
            }
            return;
        }
        if (Utils.isAlpha(currentItemStt)){
            if (!isReachedLimit(currentSelectedItem)) {
                continuousLineAddForAlphaStt(currentSelectedItem);
            }
            return;
        }
        if (Utils.isNumeric(currentItemStt)){
            continuousLineAddForNumericStt(currentSelectedItem);
        }
    }

    private void continuousLineAddForNumericStt(TreeItem<BangNoiThat> currentSelectedItem) {
        TreeItem<BangNoiThat> parent = currentSelectedItem.getParent();
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("1");

        String nextStt = findTheNextStt(currentSelectedItem.getValue().getSTT().getValue());
        tempNewItem.getValue().getSTT().setValue(nextStt);
        parent.getChildren().add(tempNewItem);
        selectNewItem(tempNewItem);
    }

    private void continuousLineAddForAlphaStt(TreeItem<BangNoiThat> currentSelectedItem) {
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("I");
        currentSelectedItem.getChildren().add(tempNewItem);
        currentSelectedItem.setExpanded(true);
        selectNewItem(tempNewItem);
    }

    private void continuousLineAddForRomanStt(TreeItem<BangNoiThat> currentSelectedItem) {
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem("1");
        currentSelectedItem.getChildren().add(tempNewItem);
        currentSelectedItem.setExpanded(true);
        selectNewItem(tempNewItem);
    }
    private void selectNewItem(TreeItem<BangNoiThat> tempNewItem){
        TableNoiThat.getSelectionModel().clearSelection();
        TableNoiThat.getSelectionModel().select(tempNewItem);
    }
    public static String findTheNextStt(String stt) {
        if (Utils.RomanNumber.isRoman(stt)){
            int nextStt = Utils.RomanNumber.romanToInt(stt) + 1;
            return Utils.RomanNumber.toRoman(nextStt);
        }
        if (Utils.isAlpha(stt)) {
            char nextLetter = (char) (stt.charAt(0) + 1);
            return String.valueOf(nextLetter);
        }
        if (Utils.isNumeric(stt)){
            return String.valueOf(Integer.parseInt(stt) + 1);
        }
        return "";
    }

    public boolean isReachedLimit(TreeItem<BangNoiThat> root){
        if (root.getChildren().size() >= 30){
            PopupUtils.throwErrorSignal("Đã đạt giới hạn số lượng 30");
            return true;
        }
        return false;
    }

    public void onDeleteLine(ActionEvent event) {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()){
            return;
        }
        TableNoiThat.getSelectionModel().getSelectedItems().forEach(
                item -> {
                    if (item.getParent() != null) {
                        item.getParent().getChildren().remove(item);
                    }
                }
        );
    }
}
