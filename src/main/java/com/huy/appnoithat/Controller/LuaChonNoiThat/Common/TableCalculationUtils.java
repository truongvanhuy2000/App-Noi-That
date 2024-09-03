package com.huy.appnoithat.Controller.LuaChonNoiThat.Common;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TableCalculationUtils {
    private static final String MET_DAI = "mét dài";
    private static final String MET_VUONG = "mét vuông";

    public static void calculateBangThanhToan(TableView<BangThanhToan> bangThanhToan, Long tongTien) {
        double datCocThietKePercentage = getPercentageFromHeader(bangThanhToan.getColumns().get(1).getText());
        double datCocThiCongPercentage = getPercentageFromHeader(bangThanhToan.getColumns().get(2).getText());
        double hangDenChanCongTrinhPercentage = getPercentageFromHeader(bangThanhToan.getColumns().get(3).getText());

        long datCocThietKe10 = round(tongTien * (datCocThietKePercentage / 100));
        long datCocThiCong30 = round(tongTien * (datCocThiCongPercentage / 100));
        long hangDenChanCongTrinh50 = round(tongTien * (hangDenChanCongTrinhPercentage / 100));
        long nghiemThuQuyet = tongTien - datCocThietKe10 - datCocThiCong30 - hangDenChanCongTrinh50;

        bangThanhToan.getItems().get(0).setDatCocThietKe10(datCocThietKe10);
        bangThanhToan.getItems().get(0).setDatCocThiCong30(datCocThiCong30);
        bangThanhToan.getItems().get(0).setHangDenChanCongTrinh50(hangDenChanCongTrinh50);
        bangThanhToan.getItems().get(0).setNghiemThuQuyet(nghiemThuQuyet);
        bangThanhToan.getItems().get(0).setTongTien(tongTien);
    }

    public static void calculateBangThanhToan(BangThanhToan bangThanhToan, BangThanhToan.Percentage percentage) {
        long tongTien = bangThanhToan.getTongTien().getValue();
        int datCocThietKePercentage = percentage.getDatCocThietKePercentage().getValue();
        int datCocThiCongPercentage = percentage.getDatCocThiCongPercentage().getValue();
        int hangDenChanCongTrinhPercentage = percentage.getHangDenChanCongTrinhPercentage().getValue();

        long datCocThietKe10 = round(tongTien * ((double) datCocThietKePercentage / 100));
        long datCocThiCong30 = round(tongTien * ((double) datCocThiCongPercentage / 100));
        long hangDenChanCongTrinh50 = round(tongTien * ((double) hangDenChanCongTrinhPercentage / 100));
        long nghiemThuQuyet = tongTien - datCocThietKe10 - datCocThiCong30 - hangDenChanCongTrinh50;

        bangThanhToan.setDatCocThietKe10(datCocThietKe10);
        bangThanhToan.setDatCocThiCong30(datCocThiCong30);
        bangThanhToan.setHangDenChanCongTrinh50(hangDenChanCongTrinh50);
        bangThanhToan.setNghiemThuQuyet(nghiemThuQuyet);
        bangThanhToan.setTongTien(tongTien);
    }

    public static Double getPercentageFromHeader(String header) {
        String percentage = header.strip().trim().split(":")[1].split("%")[0];
        return Double.parseDouble(percentage);
    }

    /**
     * @param chieuDai
     * @param chieuCao
     * @param rong
     * @param donVi    This method will calculate the khoi luong of the item
     * @return
     */
    public static Double calculateKhoiLuong(Double chieuDai, Double chieuCao, Double rong, String donVi) {
        double khoiLuong;
        if (donVi == null || donVi.isEmpty()) {
            return 0.0;
        }
        if (donVi.trim().equalsIgnoreCase(MET_DAI)) {
            if (chieuDai == 0.0) {
                return 0.0;
            }
            khoiLuong = chieuDai / 1000;
        } else if (donVi.trim().equalsIgnoreCase(MET_VUONG)) {
            if (chieuDai == 0.0 || chieuCao == 0.0) {
                return 0.0;
            }
            khoiLuong = chieuDai * chieuCao / 1000000;
        } else {
            khoiLuong = 1.0;
        }
        return roundToDecimal(khoiLuong, 1);
    }

    /**
     * @param value   Double value that need to be round
     * @param pattern Patter format is 0.0, 0.00,.....
     * @return
     */
    public static double roundToDecimal(double value, int decimalRounding) {
        BigDecimal salary = new BigDecimal(value);
        // This is to fix the problem with number like this 123.35 can't be round to 123.4
        // https://stackoverflow.com/questions/47781393/java-roundingmode-half-up-doesnt-round-up-as-expected
        return salary.setScale(decimalRounding + 1, RoundingMode.HALF_UP)
                .setScale(decimalRounding, RoundingMode.HALF_UP).doubleValue();
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
            item.getValue().setThanhTien(tongTien);
        }
        // Set the calculated total cost to the 'ThanhTien' property of the current item
    }


    /**
     * Recalculates the total cost (Tong Tien) of all TreeItems in the hierarchical structure under the given TreeItem.
     * This method recursively updates the 'ThanhTien' property for each item based on the sum of its children's 'ThanhTien' values.
     * If an item has no children, its 'ThanhTien' is set to 0.
     *
     * @param item The TreeItem for which to recalculate the total cost and its children's total costs.
     */
    public static void recalculateAllTongTien(TreeTableView<BangNoiThat> bangNoiThat) {
        TreeItem<BangNoiThat> root = bangNoiThat.getRoot();
        // Base case: If the item is null, return without any computation
        if (root == null) {
            return;
        }
        Platform.runLater(() -> {
            if (root.getChildren().isEmpty()) {
                root.getValue().setThanhTien(0L);
            }
            // Iterate through children of the current item
            for (TreeItem<BangNoiThat> child : root.getChildren()) {
                for (TreeItem<BangNoiThat> grandChild : child.getChildren()) {
                    calculateTongTien(grandChild);
                }
                calculateTongTien(child);
            }
            // If the item has no children, set its 'ThanhTien' to 0
            calculateTongTien(root);
        });
    }

    public static void recalculateBangNoiThat(BangNoiThat bangNoiThat) {
        Double dai = bangNoiThat.getDai().getValue();
        Double rong = bangNoiThat.getRong().getValue();
        Double cao = bangNoiThat.getCao().getValue();
        String donvi = bangNoiThat.getDonVi().getValue();
        Long dongia = bangNoiThat.getDonGia().getValue();
        Double khoiluong = TableCalculationUtils.calculateKhoiLuong(dai, cao, rong, donvi);
        Long thanhTien = TableCalculationUtils.calculateThanhTien(khoiluong, dongia);
        bangNoiThat.setKhoiLuong(khoiluong);
        bangNoiThat.setThanhTien(thanhTien);
    }

    public static long round(double input) {
        return Math.round(input / 1000000) * 1000000;
    }

}
