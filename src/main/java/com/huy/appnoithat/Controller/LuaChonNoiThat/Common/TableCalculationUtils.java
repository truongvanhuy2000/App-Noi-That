package com.huy.appnoithat.Controller.LuaChonNoiThat.Common;

import com.huy.appnoithat.Common.CalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;

public class TableCalculationUtils {
    private static final String MET_DAI = "mét dài";
    private static final String MET_VUONG = "mét vuông";
    public static void calculateBangThanhToan(TableView<BangThanhToan> bangThanhToan, Long tongTien) {
        Long datCocThietKe10 = round(tongTien * 0.1);
        Long datCocThiCong30 = round(tongTien * 0.3);
        Long hangDenChanCongTrinh50 = round(tongTien * 0.5);
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
     * Recursively calculates the total cost (Tong Tien) of the given TreeItem and its children in the TreeTableView.
     * Updates the 'ThanhTien' property of each TreeItem based on the sum of its children's 'ThanhTien' values.
     * This method ensures that the total cost is computed for the entire hierarchical structure.
     *
     * @param item The TreeItem for which the total cost is calculated.
     */
    public static void calculateTongTien(TreeItem<BangNoiThat> item) {
        Long tongTien = 0L;

        // Base case: If the item is null, return without any computation
        if (item == null) {
            return;
        }

        // Iterate through children of the current item and sum their 'ThanhTien' values
        for (TreeItem<BangNoiThat> child : item.getChildren()) {
            tongTien += child.getValue().getThanhTien().getValue();
        }
        // Set the calculated total cost to the 'ThanhTien' property of the current item
        item.getValue().setThanhTien(tongTien);
        calculateTongTien(item.getParent());
    }


    /**
     * Recalculates the total cost (Tong Tien) of all TreeItems in the hierarchical structure under the given TreeItem.
     * This method recursively updates the 'ThanhTien' property for each item based on the sum of its children's 'ThanhTien' values.
     * If an item has no children, its 'ThanhTien' is set to 0.
     *
     * @param item The TreeItem for which to recalculate the total cost and its children's total costs.
     */
    public static void recalculateAllTongTien(TreeItem<BangNoiThat> item) {
        // Base case: If the item is null, return without any computation
        if (item == null) {
            return;
        }

        // Iterate through children of the current item
        for (TreeItem<BangNoiThat> child : item.getChildren()) {
            for(TreeItem<BangNoiThat> grandChild : child.getChildren()) {
                calculateTongTien(grandChild);
            }
            calculateTongTien(child);
        }
        // If the item has no children, set its 'ThanhTien' to 0
        if (item.getChildren().isEmpty()) {
            item.getValue().setThanhTien(0L);
        }
    }

    public static long round(double input) {
        long i = (long) Math.ceil(input);
        return Math.round(input/1000000) * 1000000;
    };

}
