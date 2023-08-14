package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomComboboxCell;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import com.huy.appnoithat.Shared.Utils;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

import java.util.HashMap;
import java.util.List;

public class VatLieuCollumHandler {
    private ObservableList<String> vatLieuList;
    private final LuaChonNoiThatService luaChonNoiThatService;
    HashMap<String, ThongSo> vatLieuThongSoHashMap = new HashMap<>();
    public VatLieuCollumHandler(ObservableList<String> vatLieuList) {
        this.vatLieuList = vatLieuList;
        luaChonNoiThatService = new LuaChonNoiThatService();
    }
    public TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param){
        return new CustomComboboxCell(vatLieuList);
    }
    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param){
        return param.getValue().getValue().getVatLieu();
    }
    public void onEditCommitVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String vatLieu = event.getNewValue();
        event.getRowValue().getValue().setVatLieu(vatLieu);
        ThongSo coresspondingThongSo = vatLieuThongSoHashMap.get(vatLieu);
        if (coresspondingThongSo == null){
            return;
        }
        event.getRowValue().getValue().setDai(coresspondingThongSo.getDai());
        event.getRowValue().getValue().setRong(9F);
        event.getRowValue().getValue().setCao(8F);
        event.getRowValue().getValue().setDonGia(coresspondingThongSo.getDon_gia());
        event.getRowValue().getValue().setDonVi(coresspondingThongSo.getDon_vi());
    }
    public void onStartEditVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        List<String> items;
        
        String hangMuc = currentItem.getValue().getHangMuc().getValue();
        String noiThat = currentItem.getParent().getValue().getHangMuc().getValue();
        String phongCach = currentItem.getParent().getParent().getValue().getHangMuc().getValue();
        List<VatLieu> vatLieus = luaChonNoiThatService.findVatLieuListByParentsName(phongCach, noiThat, hangMuc);
        if (vatLieus == null){
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