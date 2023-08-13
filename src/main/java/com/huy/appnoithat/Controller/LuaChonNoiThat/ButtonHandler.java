package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Shared.ErrorUtils;
import com.huy.appnoithat.Shared.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

public class ButtonHandler {
    private TreeTableView<BangNoiThat> TableNoiThat;

    public ButtonHandler(TreeTableView<BangNoiThat> tableNoiThat) {
        this.TableNoiThat = tableNoiThat;
    }
    public void addNewLine(ActionEvent event) {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()){
            return;
        }

        if (isReachedLimit(TableNoiThat.getRoot())){
            ErrorUtils.throwErrorSignal("Đã đạt giới hạn số lượng 30");
            return;
        }
        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        TreeItem<BangNoiThat> parent = currentSelectedItem.getParent();

        parent.getChildren().add()
        System.out.println("new line suppose to be added");
        parent.setExpanded(true);
    }
    public void continuousLineAdd(ActionEvent event) {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()){
            return;
        }
        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        TreeItem<BangNoiThat> parent = currentSelectedItem.getParent();
        String stt = parent.getChildren().get(parent.getChildren().size() - 1).getValue().getSTT().getValue();
        String nextStt = findTheNextStt(stt);
        if (isReachedLimit(parent)) {
            ErrorUtils.throwErrorSignal("Đã đạt giới hạn số lượng 30");
            return;
        }
        parent.getChildren().add(new TreeItem<>(new BangNoiThat(
                nextStt, 0f, 0f, 0f, 0L,
                "", "", "", 0L, 0f)));
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
        return root.getChildren().size() >= 30;
    }

    public void onDeleteLine(ActionEvent event) {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()){
            return;
        }
        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        currentSelectedItem.getParent().getChildren().remove(currentSelectedItem);
    }
}
