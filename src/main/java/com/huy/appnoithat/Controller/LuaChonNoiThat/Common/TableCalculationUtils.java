package com.huy.appnoithat.Controller.LuaChonNoiThat.Common;

import com.huy.appnoithat.Common.CalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;

public class TableCalculationUtils {
    public static void calculateBangThanhToan(TableView<BangThanhToan> bangThanhToan, Long tongTien) {
        Long datCocThietKe10 = CalculationUtils.round(tongTien * 0.1);
        Long datCocThiCong30 = CalculationUtils.round(tongTien * 0.3);
        Long hangDenChanCongTrinh50 = CalculationUtils.round(tongTien * 0.5);
        Long nghiemThuQuyet = tongTien - datCocThietKe10 - datCocThiCong30 - hangDenChanCongTrinh50;

        bangThanhToan.getItems().get(0).setDatCocThietKe10(datCocThietKe10);
        bangThanhToan.getItems().get(0).setDatCocThiCong30(datCocThiCong30);
        bangThanhToan.getItems().get(0).setHangDenChanCongTrinh50(hangDenChanCongTrinh50);
        bangThanhToan.getItems().get(0).setNghiemThuQuyet(nghiemThuQuyet);
    }
    /**
     * @param chieuDai
     * @param chieuCao
     * @param rong
     * @param donVi    This method will calculate the khoi luong of the item
     * @return
     */
    public static Float calculateKhoiLuong(Float chieuDai, Float chieuCao, Float rong, String donVi) {
        String metDai = "mét dài";
        String metVuong = "mét vuông";
        Float khoiLuong = 0f;
        if (donVi.trim().equalsIgnoreCase(metDai)) {
            if (chieuDai == 0f) {
                return 0f;
            }
            khoiLuong = chieuDai / 1000;
            return khoiLuong;
        }

        if (donVi.trim().equalsIgnoreCase(metVuong)) {
            if (chieuDai == 0f || chieuCao == 0f) {
                return 0f;
            }
            khoiLuong = chieuDai * chieuCao / 1000000;
        } else {
            khoiLuong = 1f;
        }
        return khoiLuong;
    }

    /**
     * @param khoiLuong
     * @param donGia    This method will calculate the thanh tien of the item
     * @return
     */
    public static Long calculateThanhTien(Float khoiLuong, Long donGia) {
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
}
