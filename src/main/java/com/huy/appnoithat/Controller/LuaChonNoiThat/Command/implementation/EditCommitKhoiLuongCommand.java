package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Utils.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class EditCommitKhoiLuongCommand implements Command {
    private final Logger LOGGER = LogManager.getLogger(this);

    private final TreeTableColumn. CellEditEvent<BangNoiThat, Double> event;
    private Memento bangNoiThatSnapshot;
    private TreeTableView<BangNoiThat> treeTableView;

    @Override
    public void execute() {
        BangNoiThat bangNoiThat = event.getRowValue().getValue();
        treeTableView = event.getTreeTableView();
        if (bangNoiThat == null) {
            LOGGER.warn("EditCommitKhoiLuongCommand: bangnoithat is null");
            return;
        }
        bangNoiThatSnapshot = bangNoiThat.createSnapshot();

        double khoiLuong = event.getNewValue();
        long donGia = event.getRowValue().getValue().getDonGia().getValue();
        long thanhTien = TableCalculationUtils.calculateThanhTien(khoiLuong, donGia);
        event.getRowValue().getValue().setThanhTien(thanhTien);
        event.getRowValue().getValue().setKhoiLuong(khoiLuong);
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
