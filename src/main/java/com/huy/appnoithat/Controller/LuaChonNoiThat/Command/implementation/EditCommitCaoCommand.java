package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class EditCommitCaoCommand implements Command {
    private final Logger LOGGER = LogManager.getLogger(this);
    private final TreeTableColumn.CellEditEvent<BangNoiThat, Double> event;
    private TreeTableView<BangNoiThat> treeTableView;
    private Memento bangNoiThatSnapshot;

    @Override
    public void execute() {
        LOGGER.debug("Execute EditCommitCaoCommand");
        BangNoiThat bangNoiThat = event.getRowValue().getValue();
        treeTableView = event.getTreeTableView();
        if (bangNoiThat == null) {
            LOGGER.warn("EditCommitCaoCommand: Bang noi that is null");
            return;
        }
        bangNoiThatSnapshot = bangNoiThat.createSnapshot();

        bangNoiThat.setCao(event.getNewValue());

        TableCalculationUtils.recalculateBangNoiThat(bangNoiThat);
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
