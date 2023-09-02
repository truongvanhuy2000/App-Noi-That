package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Shared.PopupUtils;
import com.huy.appnoithat.Shared.Utils;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class TableUtils {
    public static TreeItem<BangNoiThat> findYoungestChildern(TreeItem<BangNoiThat> root){
        if (root.isLeaf()){
            throw new IllegalArgumentException("Root must not be leaf");
        }
        while (true){
            if (!root.getChildren().isEmpty()){
                return root;
            }
        }
    }
    public static boolean isALlowedToEdit(TreeTableColumn.CellEditEvent<BangNoiThat, String> event){
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        if (!Utils.isNumeric(currentItem.getValue().getSTT().getValue())){
            PopupUtils.throwErrorSignal("Không được phép chọn trường cho mục ");
            event.consume();
            return false;
        }
        return true;
    }

    public static Float calculateKhoiLuong(float chieuDai, float chieuCao, float rong, String donVi){
        String metDai = "mét dài";
        String metVuong = "mét vuông";
        Float khoiLuong = 0f;
        if (donVi.trim().equalsIgnoreCase(metDai)){
            if(chieuDai == 0f){
                return 0f;
            }
            khoiLuong = chieuDai/1000;
        }

        if (donVi.trim().equalsIgnoreCase(metVuong)){
            if(chieuDai == 0f || chieuCao == 0f){
                return 0f;
            }
            khoiLuong = chieuDai*chieuCao/1000000;
        }
        return khoiLuong;
    }

    public static Long calculateThanhTien(float khoiLuong, long donGia){
        return (long) (khoiLuong*donGia);
    }

    public static void calculateTongTien(TreeItem<BangNoiThat> item){
        Long tongTien = 0L;
        if (item == null){
            return;
        }
        for (TreeItem<BangNoiThat> child : item.getChildren()){
            tongTien += child.getValue().getThanhTien().getValue();
        }
        item.getValue().setThanhTien(tongTien);
        calculateTongTien(item.getParent());
    }

    public static boolean isEditable(TreeTableView<BangNoiThat> TableNoiThat){
        if (TableNoiThat.getSelectionModel().getSelectedItem() == null){
            return false;
        }
        String stt = TableNoiThat.getSelectionModel().getSelectedItem().getValue().getSTT().getValue();
        return Utils.isNumeric(stt);
    }
}
