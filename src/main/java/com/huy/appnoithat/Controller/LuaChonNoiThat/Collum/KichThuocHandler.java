package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;


public class KichThuocHandler {
    //    final static Logger LOGGER = LogManager.getLogger(KichThuocHandler.class);
    private TreeTableView<BangNoiThat> TableNoiThat;
    private TreeTableColumn<BangNoiThat, Double> Cao, Dai, Rong;
    private TreeTableColumn<BangNoiThat, Long> DonGia;

    public KichThuocHandler(TreeTableView<BangNoiThat> tableNoiThat,
                            TreeTableColumn<BangNoiThat, Double> cao,
                            TreeTableColumn<BangNoiThat, Double> dai,
                            TreeTableColumn<BangNoiThat, Double> rong,
                            TreeTableColumn<BangNoiThat, Long> donGia) {
        TableNoiThat = tableNoiThat;
        Cao = cao;
        Dai = dai;
        Rong = rong;
        DonGia = donGia;
    }

    public void onCommitEditKichThuoc(TreeTableColumn.CellEditEvent<BangNoiThat, ?> event) {
        if (event.getSource().equals(Cao)) {
            event.getRowValue().getValue().setCao((double) event.getNewValue());
        }
        if (event.getSource().equals(Dai)) {
            event.getRowValue().getValue().setDai((double) event.getNewValue());
        }
        if (event.getSource().equals(Rong)) {
            event.getRowValue().getValue().setRong((double) event.getNewValue());
        }
        if (event.getSource().equals(DonGia)) {
            event.getRowValue().getValue().setDonGia((long) event.getNewValue());
        }
        Double dai = event.getRowValue().getValue().getDai().getValue();
        Double rong = event.getRowValue().getValue().getRong().getValue();
        Double cao = event.getRowValue().getValue().getCao().getValue();
        String donvi = event.getRowValue().getValue().getDonVi().getValue();
        Double khoiluong = TableCalculationUtils.calculateKhoiLuong(dai, rong, cao, donvi);
        Long dongia = event.getRowValue().getValue().getDonGia().getValue();
        Long thanhTien = TableCalculationUtils.calculateThanhTien(khoiluong, dongia);
        event.getRowValue().getValue().setKhoiLuong(khoiluong);
        event.getRowValue().getValue().setThanhTien(thanhTien);

        TableCalculationUtils.calculateTongTien(event.getRowValue().getParent());
    }
}
