package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.ItemTypeUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;


public class STTCollumHandler {
    private final TreeTableView<BangNoiThat> TableNoiThat;


    /**
     * Handler class for managing STT (Serial Number) column in a TreeTableView of BangNoiThat items.
     * Handles cell editing and provides custom cell factory and cell value factory for the STT column.
     *
     * @param tableNoiThat The TreeTableView representing the table of items.
     */
    public STTCollumHandler(TreeTableView<BangNoiThat> tableNoiThat) {
        this.TableNoiThat = tableNoiThat;
    }


    /**
     * Handles the commit event when editing the STT column in the TreeTableView.
     * Calls the 'handleInputedSTT' method to handle the inputted STT value and clears the selection.
     *
     * @param event The CellEditEvent containing information about the edit event.
     */
    public void onEditCommitSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        handleInputedSTT(event);
        event.getTreeTableView().getSelectionModel().clearSelection();
    }


    /**
     * Handles the inputted STT value during editing. Updates the STT property of the BangNoiThat item.
     * (Deprecated methods below handled specific scenarios related to STT input; these are commented out).
     *
     * @param event The CellEditEvent containing information about the edit event.
     */
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
//
//
//    @Deprecated
//    private void handleComitedAlphaSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item) {
//        TreeItem<BangNoiThat> currentItem = event.getRowValue();
//        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
//        currentItem.getParent().getChildren().remove(currentItem);
//        TableNoiThat.getRoot().getChildren().add(newItem);
//    }
//
//    // This mean we are choosing noi that
//    @Deprecated
//    public void handleCommitedRomanSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item) {
//        TreeItem<BangNoiThat> currentItem = event.getRowValue();
//        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
//        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList = findPhongCachList(currentItem);
//        if (tempPhongCachList == null) {
//            return;
//        }
//        if (tempPhongCachList.isEmpty()) {
////            PopupUtils.throwErrorNotification("Chưa lựa chọn phong cách");
////            event.consume();
//            return;
//        }
//        removeFromParent(currentItem);
//        addNodeToTheYoungestLeaf(tempPhongCachList, newItem);
//    }
//
//    // This mean we are choosing hang muc
//    @Deprecated
//    public void handleCommitedNumericSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item) {
//        TreeItem<BangNoiThat> currentItem = event.getRowValue();
//        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
//        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList = findPhongCachList(currentItem);
//        if (tempPhongCachList == null) {
//            return;
//        }
//        if (tempPhongCachList.isEmpty()) {
////            PopupUtils.throwErrorNotification("Chưa lựa chọn phong cách");
////            event.consume();
//            return;
//        }
//        removeFromParent(currentItem);
//        ObservableList<TreeItem<BangNoiThat>> tempNoiThatList = tempPhongCachList.get(tempPhongCachList.size() - 1).getChildren();
//        if (tempNoiThatList.isEmpty()) {
////            PopupUtils.throwErrorNotification("Chưa lựa chọn phong cách");
////            event.consume();
//            return;
//        }
//        addNodeToTheYoungestLeaf(tempNoiThatList, newItem);
//    }
//
//    /**
//     * Adds the provided TreeItem 'newItem' to the youngest leaf in the given 'leafList'.
//     * Finds the youngest leaf in the 'leafList' and appends the 'newItem' as its child.
//     * Ensures the youngest leaf is expanded after adding the new child TreeItem.
//     *
//     * @param leafList The list of TreeItems representing potential leaf nodes.
//     * @param newItem  The TreeItem to be added as a child to the youngest leaf in the 'leafList'.
//     */
//    @Deprecated
//    private ObservableList<TreeItem<BangNoiThat>> findPhongCachList(TreeItem<BangNoiThat> currentItem) {
//        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList;
//        TreeItem<BangNoiThat> root = TableNoiThat.getRoot();
//        tempPhongCachList = root.getChildren();
//        return tempPhongCachList;
//    }
//    @Deprecated
//    private void removeFromParent(TreeItem<BangNoiThat> node) {
//        node.getParent().getChildren().remove(node);
//    }
//    @Deprecated
//    private void addNodeToTheYoungestLeaf(ObservableList<TreeItem<BangNoiThat>> leafList, TreeItem<BangNoiThat> newItem) {
//        TreeItem<BangNoiThat> youngestLeaf = leafList.get(leafList.size() - 1);
//        youngestLeaf.getChildren().add(newItem);
//        youngestLeaf.setExpanded(true);
//    }


    /**
     * Provides a custom cell factory for the STT column in the TreeTableView.
     * Customizes the appearance of STT cells based on their content.
     *
     * @param param The TreeTableColumn instance for which the custom cell factory is provided.
     * @return A customized TreeTableCell for the STT column.
     */
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
                setText(ItemTypeUtils.getIdFromFullId(item));
                TreeTableRow<BangNoiThat> currentRow = getTableRow();
                if (!isEmpty()) {
                    switch (ItemTypeUtils.determineItemType(getItem())) {
                        case ROMAN -> currentRow.setStyle("-fx-font-weight: bold");
                        case AlPHA -> currentRow.setStyle("-fx-font-weight: bold; -fx-font-size: 14px");
                        case NUMERIC -> currentRow.setStyle("-fx-font-weight: normal");
                        default -> {}
                    }
                }
            }
        };
    }


    /**
     * Provides a custom cell value factory for the STT column in the TreeTableView.
     * Retrieves the 'STT' property value from the BangNoiThat object associated with the current cell.
     *
     * @param param The CellDataFeatures instance representing the data for the current cell.
     * @return An ObservableValue<String> representing the 'STT' property of the current cell's data.
     *         Returns null if the current row's data is null or STT value is empty.
     */
    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param) {
        if (param.getValue() == null) {
            return null;
        }
        SimpleStringProperty tempSTT = param.getValue().getValue().getSTT();
        return tempSTT.getValue().isEmpty() ? null : tempSTT;
    }
}
