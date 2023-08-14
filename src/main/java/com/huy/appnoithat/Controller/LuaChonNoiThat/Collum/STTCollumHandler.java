package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomEditingCell;
import com.huy.appnoithat.Shared.ErrorUtils;
import com.huy.appnoithat.Shared.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class STTCollumHandler {
    private TreeTableView<BangNoiThat> TableNoiThat;
    private final static Logger logger = LogManager.getLogger(STTCollumHandler.class);

    public STTCollumHandler(TreeTableView<BangNoiThat> tableNoiThat) {
        this.TableNoiThat = tableNoiThat;
    }

    public void onEditCommitSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        handleInputedSTT(event);
        event.getTreeTableView().getSelectionModel().clearSelection();
    }
    private void handleInputedSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event)
    {
        String newValue = event.getNewValue();
        event.getRowValue().getValue().setSTT(newValue);
        if(Utils.RomanNumber.isRoman(newValue)){
            handleCommitedRomanSTT(event, newValue);
            return;
        }
        if (Utils.isAlpha(newValue)){
            handleComitedAlphaSTT(event, newValue);
            return;
        }
        if (Utils.isNumeric(newValue)){
            handleCommitedNumericSTT(event, newValue);
        }
    }

    private void handleComitedAlphaSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
        currentItem.getParent().getChildren().remove(currentItem);
        TableNoiThat.getRoot().getChildren().add(newItem);
    }

    // This mean we are choosing noi that
    public void handleCommitedRomanSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item){
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList = findPhongCachList(currentItem);
        if (tempPhongCachList == null){
            logger.error("tempPhongCachList is null");
            return;
        }
        if (tempPhongCachList.isEmpty()){
            ErrorUtils.throwErrorSignal("Chưa lựa chọn phong cách");
            event.consume();
            return;
        }
        removeFromParent(currentItem);
        addNodeToTheYoungestLeaf(tempPhongCachList, newItem);
    }
    // This mean we are choosing hang muc
    public void handleCommitedNumericSTT(TreeTableColumn.CellEditEvent<BangNoiThat, String> event, String item){
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(currentItem.getValue());
        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList = findPhongCachList(currentItem);
        assert tempPhongCachList != null;
        if (tempPhongCachList.isEmpty()){
            ErrorUtils.throwErrorSignal("Chưa lựa chọn phong cách");
            event.consume();
            return;
        }
        removeFromParent(currentItem);
        ObservableList<TreeItem<BangNoiThat>> tempNoiThatList = tempPhongCachList.get(tempPhongCachList.size() - 1).getChildren();
        if (tempNoiThatList.isEmpty()){
            ErrorUtils.throwErrorSignal("Chưa lựa chọn phong cách");
            event.consume();
            return;
        }
        addNodeToTheYoungestLeaf(tempNoiThatList, newItem);

    }

    private ObservableList<TreeItem<BangNoiThat>> findPhongCachList(TreeItem<BangNoiThat> currentItem){
        ObservableList<TreeItem<BangNoiThat>> tempPhongCachList;
        TreeItem<BangNoiThat> root = TableNoiThat.getRoot();
        tempPhongCachList = root.getChildren();
        return tempPhongCachList;
    }

    private void removeFromParent(TreeItem<BangNoiThat> node){
        node.getParent().getChildren().remove(node);
    }

    private void addNodeToTheYoungestLeaf(ObservableList<TreeItem<BangNoiThat>> leafList, TreeItem<BangNoiThat> newItem){
        TreeItem<BangNoiThat> youngestLeaf = leafList.get(leafList.size() - 1);
        youngestLeaf.getChildren().add(newItem);
        youngestLeaf.setExpanded(true);
        makeTreeNotCollapsed(youngestLeaf);
    }

    public TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param){
        return new CustomEditingCell<>();
    }

    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param){
        SimpleStringProperty tempSTT = param.getValue().getValue().getSTT();
        return tempSTT.getValue().isEmpty() ? null : tempSTT;
    }

    private void makeTreeNotCollapsed(TreeItem<BangNoiThat> item){
//        item.addEventHandler(TreeItem.branchCollapsedEvent(),
//                (EventHandler<TreeItem.TreeModificationEvent<String>>) event -> event.getTreeItem().setExpanded(true));
    }
}
