package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;


public class KichThuocHandler {
    //    final static Logger LOGGER = LogManager.getLogger(KichThuocHandler.class);
    private TreeTableView<BangNoiThat> TableNoiThat;
    private TreeTableColumn<BangNoiThat, Float> Cao, Dai, Rong;

    public KichThuocHandler(TreeTableView<BangNoiThat> tableNoiThat,
                            TreeTableColumn<BangNoiThat,
                                    Float> cao,
                            TreeTableColumn<BangNoiThat,
                                    Float> dai,
                            TreeTableColumn<BangNoiThat,
                                    Float> rong) {
        TableNoiThat = tableNoiThat;
        Cao = cao;
        Dai = dai;
        Rong = rong;
    }

    public void onCommitEditKichThuoc(TreeTableColumn.CellEditEvent<BangNoiThat, Float> event) {
        if (event.getSource().equals(Cao)) {
            event.getRowValue().getValue().setCao(event.getNewValue());
        }
        if (event.getSource().equals(Dai)) {
            event.getRowValue().getValue().setDai(event.getNewValue());
        }
        if (event.getSource().equals(Rong)) {
            event.getRowValue().getValue().setRong(event.getNewValue());
        }
        float dai = event.getRowValue().getValue().getDai().getValue();
        float rong = event.getRowValue().getValue().getRong().getValue();
        float cao = event.getRowValue().getValue().getCao().getValue();
        String donvi = event.getRowValue().getValue().getDonVi().getValue();
        float khoiluong = TableCalculationUtils.calculateKhoiLuong(dai, rong, cao, donvi);
        System.out.println(khoiluong);
        Long dongia = event.getRowValue().getValue().getDonGia().getValue();
        long thanhTien = TableCalculationUtils.calculateThanhTien(khoiluong, dongia);
        System.out.println(thanhTien);
        event.getRowValue().getValue().setKhoiLuong(khoiluong);
        event.getRowValue().getValue().setThanhTien(thanhTien);

        TableCalculationUtils.calculateTongTien(event.getRowValue().getParent());
    }
}
