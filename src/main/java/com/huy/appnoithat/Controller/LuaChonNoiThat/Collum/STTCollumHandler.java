package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomEditingCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.*;


public class STTCollumHandler {
    private final TreeTableView<BangNoiThat> TableNoiThat;

    public STTCollumHandler(TreeTableView<BangNoiThat> tableNoiThat) {
        this.TableNoiThat = tableNoiThat;
    }

    public void onEditCommitSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        handleInputedSTT(event);
        event.getTreeTableView().getSelectionModel().clearSelection();
    }

    private void handleInputedSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String newValue = event.getNewValue();
        event.getRowValue().getValue().setSTT(newValue);
//        if (Utils.RomanNumber.isRoman(newValue)) {
//            handleCommitedRomanSTT(event, newValue);
//            return;
//        }
//        if (Utils.isAlpha(newValue)) {
//            handleComitedAlphaSTT(event, newValue);
//            return;
//        }
//        if (Utils.isNumeric(newValue)) {
//            handleCommitedNumericSTT(event, newValue);
//        }
    }
    @Deprecated
    private void handleComitedAlphaSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
        currentItem.getParent().getChildren().remove(currentItem);
        TableNoiThat.getRoot().getChildren().add(newItem);
    }

    // This mean we are choosing noi that
    @Deprecated
    public void handleCommitedRomanSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList = findPhongCachList(currentItem);
        if (tempPhongCachList == null) {
            return;
        }
        if (tempPhongCachList.isEmpty()) {
//            PopupUtils.throwErrorSignal("Chưa lựa chọn phong cách");
//            event.consume();
            return;
        }
        removeFromParent(currentItem);
        addNodeToTheYoungestLeaf(tempPhongCachList, newItem);
    }

    // This mean we are choosing hang muc
    @Deprecated
    public void handleCommitedNumericSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList = findPhongCachList(currentItem);
        if (tempPhongCachList == null) {
            return;
        }
        if (tempPhongCachList.isEmpty()) {
//            PopupUtils.throwErrorSignal("Chưa lựa chọn phong cách");
//            event.consume();
            return;
        }
        removeFromParent(currentItem);
        ObservableList<TreeItem<BangNoiThat>> tempNoiThatList = tempPhongCachList.get(tempPhongCachList.size() - 1).getChildren();
        if (tempNoiThatList.isEmpty()) {
//            PopupUtils.throwErrorSignal("Chưa lựa chọn phong cách");
//            event.consume();
            return;
        }
        addNodeToTheYoungestLeaf(tempNoiThatList, newItem);
    }
    @Deprecated
    private ObservableList<TreeItem<BangNoiThat>> findPhongCachList(TreeItem<BangNoiThat> currentItem) {
        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList;
        TreeItem<BangNoiThat> root = TableNoiThat.getRoot();
        tempPhongCachList = root.getChildren();
        return tempPhongCachList;
    }
    @Deprecated
    private void removeFromParent(TreeItem<BangNoiThat> node) {
        node.getParent().getChildren().remove(node);
    }
    @Deprecated
    private void addNodeToTheYoungestLeaf(ObservableList<TreeItem<BangNoiThat>> leafList, TreeItem<BangNoiThat> newItem) {
        TreeItem<BangNoiThat> youngestLeaf = leafList.get(leafList.size() - 1);
        youngestLeaf.getChildren().add(newItem);
        youngestLeaf.setExpanded(true);
    }

    public TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param) {
        return new TreeTableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                setText(item);
                TreeTableRow<BangNoiThat> currentRow = getTableRow();
                if (!isEmpty()) {
                    if (Utils.isNumeric(getItem())) {
                        currentRow.setStyle("-fx-font-weight: normal");
                        return;
                    }
                    if (Utils.RomanNumber.isRoman(getItem())) {
                        currentRow.setStyle("-fx-font-weight: bold");
                        return;
                    }
                    if (Utils.isAlpha(getItem())) {
                        currentRow.setStyle("-fx-font-weight: bold; -fx-font-size: 14px");
                        return;
                    }
                }
            }
        };
    }

    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param) {
        if (param.getValue() == null) {
            return null;
        }
        SimpleStringProperty tempSTT = param.getValue().getValue().getSTT();
        return tempSTT.getValue().isEmpty() ? null : tempSTT;
    }
}
