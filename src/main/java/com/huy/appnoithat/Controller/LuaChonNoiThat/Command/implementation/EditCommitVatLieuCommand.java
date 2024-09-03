package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.DataModel.Entity.ThongSo;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Objects;

@RequiredArgsConstructor
public class EditCommitVatLieuCommand implements Command {
    private final Logger LOGGER = LogManager.getLogger(this);
    private final HashMap<String, ThongSo> vatLieuThongSoHashMap;
    private final TreeTableColumn.CellEditEvent<BangNoiThat, String> event;
    private Memento bangNoiThatSnapshot;
    private TreeTableView<BangNoiThat> bangNoiThatTreeItem;

    @Override
    public void execute() {
        BangNoiThat bangNoiThat = event.getRowValue().getValue();
        if (bangNoiThat == null) {
            return;
        }
        bangNoiThatSnapshot = bangNoiThat.createSnapshot();
        onEditCommitVatLieu(event);
    }

    @Override
    public void undo() {
        if (bangNoiThatSnapshot == null) {
            return;
        }
        bangNoiThatSnapshot.restore();
        TableCalculationUtils.recalculateAllTongTien(bangNoiThatTreeItem);
    }

    public void onEditCommitVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String vatLieu = event.getNewValue();
        BangNoiThat rowValue = event.getRowValue().getValue();
        bangNoiThatTreeItem = event.getTreeTableView();
        if (rowValue == null) {
            LOGGER.warn("EditCommitVatLieuCommand: row value must not be null");
            return;
        }
        rowValue.setVatLieu(vatLieu);
        ThongSo coresspondingThongSo = vatLieuThongSoHashMap.get(vatLieu);
        if (coresspondingThongSo == null) {
            LOGGER.warn("coresspondingThongSo must not be null");
            return;
        }
        Double dai = Objects.requireNonNullElse(coresspondingThongSo.getDai(), 0.0);
        Double rong = Objects.requireNonNullElse(coresspondingThongSo.getRong(), 0.0);
        Double cao = Objects.requireNonNullElse(coresspondingThongSo.getCao(), 0.0);
        Long donGia = coresspondingThongSo.getDon_gia();
        String donVi = coresspondingThongSo.getDon_vi();

        if (!dai.equals(0.0)) {
            rowValue.setDai(dai);
        }
        if (!rong.equals(0.0)) {
            rowValue.setRong(rong);
        }
        if (!cao.equals(0.0)) {
            rowValue.setCao(cao);
        }
        rowValue.setDonGia(donGia);
        rowValue.setDonVi(donVi);

        Double khoiLuong = TableCalculationUtils.calculateKhoiLuong(
                rowValue.getDai().getValue(),
                rowValue.getCao().getValue(),
                rowValue.getRong().getValue(), donVi);
        Long thanhTien = TableCalculationUtils.calculateThanhTien(khoiLuong, donGia);

        rowValue.setThanhTien(thanhTien);
        rowValue.setKhoiLuong(khoiLuong);
        TableCalculationUtils.recalculateAllTongTien(bangNoiThatTreeItem);
    }
}
