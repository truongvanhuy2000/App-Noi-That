package com.huy.appnoithat.Controller.LuaChonNoiThat.Common;

import com.huy.appnoithat.Common.CalculationUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

public class TableCalculationUtils {
    private static final String MET_DAI = "mét dài";
    private static final String MET_VUONG = "mét vuông";
    public static void calculateBangThanhToan(TableView<BangThanhToan> bangThanhToan, Long tongTien) {
        Long datCocThietKe10 = CalculationUtils.round(tongTien * 0.1);
        Long datCocThiCong30 = CalculationUtils.round(tongTien * 0.3);
        Long hangDenChanCongTrinh50 = CalculationUtils.round(tongTien * 0.5);
        Long nghiemThuQuyet = tongTien - datCocThietKe10 - datCocThiCong30 - hangDenChanCongTrinh50;

        bangThanhToan.getItems().get(0).setDatCocThietKe10(datCocThietKe10);
        bangThanhToan.getItems().get(0).setDatCocThiCong30(datCocThiCong30);
        bangThanhToan.getItems().get(0).setHangDenChanCongTrinh50(hangDenChanCongTrinh50);
        bangThanhToan.getItems().get(0).setNghiemThuQuyet(nghiemThuQuyet);
        bangThanhToan.getItems().get(0).setTongTien(tongTien);
    }
    /**
     * @param chieuDai
     * @param chieuCao
     * @param rong
     * @param donVi    This method will calculate the khoi luong of the item
     * @return
     */
    public static Double calculateKhoiLuong(Double chieuDai, Double chieuCao, Double rong, String donVi) {
        double khoiLuong = 0.0;
        if (donVi.trim().equalsIgnoreCase(MET_DAI)) {
            if (chieuDai == 0.0) {
                return 0.0;
            }
            khoiLuong = chieuDai / 1000;
            return khoiLuong;
        }

        if (donVi.trim().equalsIgnoreCase(MET_VUONG)) {
            if (chieuDai == 0.0 || chieuCao == 0.0) {
                return 0.0;
            }
            khoiLuong = chieuDai * chieuCao / 1000000;
        } else {
            khoiLuong = 1.0;
        }
        return khoiLuong;
    }

    /**
     * @param khoiLuong
     * @param donGia    This method will calculate the thanh tien of the item
     * @return
     */
    public static Long calculateThanhTien(Double khoiLuong, Long donGia) {
        if (khoiLuong == 0.0 || donGia == 0.0) {
            return 0L;
        }
        return (long) (khoiLuong * donGia);
    }

    /**
     * @param item This method will calculate the tong tien of the item
     */
    public static void calculateTongTien(TreeItem<BangNoiThat> item) {
        Long tongTien = 0L;
        if (item == null) {
            return;
        }
        for (TreeItem<BangNoiThat> child : item.getChildren()) {
            tongTien += child.getValue().getThanhTien().getValue();
        }
        item.getValue().setThanhTien(tongTien);
        calculateTongTien(item.getParent());
    }
    public static void recalculateAllTongTien(TreeItem<BangNoiThat> item) {
        if (item == null) {
            return;
        }
        for (TreeItem<BangNoiThat> child : item.getChildren()) {
            for(TreeItem<BangNoiThat> grandChild : child.getChildren()) {
                calculateTongTien(grandChild);
            }
            calculateTongTien(child);
        }
        if (item.getChildren().isEmpty()) {
            item.getValue().setThanhTien(0L);
        }
    }

}
