package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomVatLieuCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VatLieuCollumHandler {
    private final ObservableList<String> vatLieuList;
    private final LuaChonNoiThatService luaChonNoiThatService;
    HashMap<String, ThongSo> vatLieuThongSoHashMap = new HashMap<>();

    public VatLieuCollumHandler(ObservableList<String> vatLieuList) {
        this.vatLieuList = vatLieuList;
        luaChonNoiThatService = new LuaChonNoiThatService();
    }

    public TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param) {
        return new CustomVatLieuCell(vatLieuList, param.getTreeTableView());
    }

    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param) {
        if (param.getValue() == null) {
            return null;
        }
        return param.getValue().getValue().getVatLieu();
    }

    public void onEditCommitVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String vatLieu = event.getNewValue();
        event.getRowValue().getValue().setVatLieu(vatLieu);
        ThongSo coresspondingThongSo = vatLieuThongSoHashMap.get(vatLieu);
        if (coresspondingThongSo == null) {
            return;
        }
        Double dai = Objects.requireNonNullElse(coresspondingThongSo.getDai(), 0.0);
        Double rong = Objects.requireNonNullElse(coresspondingThongSo.getRong(), 0.0);
        Double cao = Objects.requireNonNullElse(coresspondingThongSo.getCao(), 0.0);
        Long donGia = coresspondingThongSo.getDon_gia();
        String donVi = coresspondingThongSo.getDon_vi();
        Double khoiLuong = TableCalculationUtils.calculateKhoiLuong(dai, cao, rong, donVi);
        Long thanhTien = TableCalculationUtils.calculateThanhTien(khoiLuong, donGia);

        event.getRowValue().getValue().setThanhTien(thanhTien);
        event.getRowValue().getValue().setKhoiLuong(khoiLuong);
        event.getRowValue().getValue().setDai(dai);
        event.getRowValue().getValue().setRong(rong);
        event.getRowValue().getValue().setCao(cao);
        event.getRowValue().getValue().setDonGia(donGia);
        event.getRowValue().getValue().setDonVi(donVi);

        TableCalculationUtils.calculateTongTien(event.getRowValue().getParent());
//        event.getTreeTableView().getSelectionModel().clearSelection();
    }

    public void onStartEditVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        List<String> items;
        if (!TableUtils.isAllowedToEdit(event)) {
            return;
        }
        String hangMuc = currentItem.getValue().getHangMuc().getValue();
        String noiThat = currentItem.getParent().getValue().getHangMuc().getValue();
        String phongCach = currentItem.getParent().getParent().getValue().getHangMuc().getValue();
        List<VatLieu> vatLieus = luaChonNoiThatService.findVatLieuListBy(phongCach, noiThat, hangMuc);
        if (vatLieus == null) {
            return;
        }
        vatLieus.forEach(vatLieu -> {
            vatLieuThongSoHashMap.put(vatLieu.getName(), vatLieu.getThongSo());
        });
        items = Utils.getObjectNameList(vatLieus);
        this.vatLieuList.clear();
        this.vatLieuList.addAll(items);
    }

}
