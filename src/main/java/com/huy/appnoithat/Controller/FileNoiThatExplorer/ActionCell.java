package com.huy.appnoithat.Controller.FileNoiThatExplorer;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.DataModel.RecentFile;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TreeTableCell;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class ActionCell extends TableCell<RecentFile, String> {
    private HBox hBox;
    Consumer<Integer> deleteAction;
    public ActionCell(Consumer<Integer> deleteAction) {
        if (hBox == null) {
            init();
        }
        this.deleteAction = deleteAction;
    }
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            setGraphic(hBox);
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(hBox);
        } else {
            setGraphic(null);
        }
    }

    private void init() {
        Button deleteButton = new Button("Delete");
        hBox = new HBox(deleteButton);
        deleteButton.setOnAction((event) -> {
            PopupUtils.throwSuccessNotification("bing chilling" + getTableRow().getIndex());
            deleteAction.accept(getTableRow().getIndex());
        });
    }
}
