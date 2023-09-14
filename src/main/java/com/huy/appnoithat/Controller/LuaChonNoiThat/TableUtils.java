package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Shared.Utils;
import javafx.event.EventHandler;
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
//            PopupUtils.throwErrorSignal("Không được phép chọn trường cho mục ");
            event.consume();
            return false;
        }
        return true;
    }

    public static Float calculateKhoiLuong(Float chieuDai, Float chieuCao, Float rong, String donVi){
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
        else {
            khoiLuong = 1f;
        }
        return khoiLuong;
    }

    public static Long calculateThanhTien(Float khoiLuong, Long donGia){
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
    public static TreeItem<BangNoiThat> createNewItem(String id) {
        TreeItem<BangNoiThat> newItem = new TreeItem<>(new BangNoiThat(id, 0f, 0f, 0f, 0L,
                "", "", "", 0L, 0f));
        newItem.addEventHandler(TreeItem.branchCollapsedEvent(),
                (EventHandler<TreeItem.TreeModificationEvent<String>>) event -> event.getTreeItem().setExpanded(true));
        newItem.setExpanded(true);
        return newItem;
    }
    public static void selectSingleItem(TreeTableView<BangNoiThat> TableNoiThat, TreeItem<BangNoiThat> item){
        TableNoiThat.getSelectionModel().clearSelection();
        TableNoiThat.getSelectionModel().select(item);
    }
}
