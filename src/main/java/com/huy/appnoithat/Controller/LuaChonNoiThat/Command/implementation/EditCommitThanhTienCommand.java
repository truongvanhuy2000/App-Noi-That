package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditCommitThanhTienCommand implements Command {
    private final TreeTableColumn. CellEditEvent<BangNoiThat, Long> event;

    private TreeTableView<BangNoiThat> treeTableView;
    private Memento bangNoiThatSnapshot;

    @Override
    public void execute() {
        BangNoiThat bangNoiThat = event.getRowValue().getValue();
        treeTableView = event.getTreeTableView();
        if (bangNoiThat == null) {
            return;
        }
        bangNoiThatSnapshot = bangNoiThat.createSnapshot();
        bangNoiThat.setThanhTien(event.getNewValue());
        TableCalculationUtils.recalculateAllTongTien(treeTableView);
    }

    @Override
    public void undo() {
        if (bangNoiThatSnapshot == null) {
            return;
        }
        bangNoiThatSnapshot.restore();
        TableCalculationUtils.recalculateAllTongTien(treeTableView);
    }
}
