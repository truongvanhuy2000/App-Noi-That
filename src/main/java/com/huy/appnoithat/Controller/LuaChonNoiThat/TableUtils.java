package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class TableUtils {
    /**
     * @param root THis method will return the youngest childern of the root
     * @return
     */
    public static TreeItem<BangNoiThat> findYoungestChildern(TreeItem<BangNoiThat> root) {
        if (root.isLeaf()) {
            throw new IllegalArgumentException("Root must not be leaf");
        }
        while (true) {
            if (!root.getChildren().isEmpty()) {
                return root;
            }
        }
    }

    /**
     * @param event This method will check if the current cell is allowed to edit
     * @return
     */
    public static boolean isAllowedToEdit(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        if (!Utils.isNumeric(currentItem.getValue().getSTT().getValue())) {
            event.consume();
            return false;
        }
        return true;
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

    /**
     * @param TableNoiThat This method will check if the current cell is editable
     * @return
     */
    public static boolean isEditable(TreeTableView<BangNoiThat> TableNoiThat) {
        if (TableNoiThat.getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        String stt = TableNoiThat.getSelectionModel().getSelectedItem().getValue().getSTT().getValue();
        return Utils.isNumeric(stt);
    }

    /**
     * @param id This method will create a new item with the given id
     * @return
     */
    public static TreeItem<BangNoiThat> createNewItem(String id) {
        TreeItem<BangNoiThat> newItem = new TreeItem<>(new BangNoiThat(id, 0f, 0f, 0f, 0L,
                "", "", "", 0L, 0f));
        newItem.addEventHandler(TreeItem.branchCollapsedEvent(),
                (EventHandler<TreeItem.TreeModificationEvent<String>>) event -> event.getTreeItem().setExpanded(true));
        newItem.setExpanded(true);
        return newItem;
    }

    /**
     * @param bangNoiThat This method will create a new item with the given BangNoiThat
     * @return
     */
    public static TreeItem<BangNoiThat> createNewItem(BangNoiThat bangNoiThat) {
        TreeItem<BangNoiThat> newItem = new TreeItem<>(bangNoiThat);
        newItem.addEventHandler(TreeItem.branchCollapsedEvent(),
                (EventHandler<TreeItem.TreeModificationEvent<String>>) event -> event.getTreeItem().setExpanded(true));
        newItem.setExpanded(true);
        return newItem;
    }

    /**
     * @param TableNoiThat This method will select the given item in the table
     * @param item
     */
    public static void selectSingleItem(TreeTableView<BangNoiThat> TableNoiThat, TreeItem<BangNoiThat> item) {
        TableNoiThat.getSelectionModel().clearSelection();
        TableNoiThat.getSelectionModel().select(item);
    }

    /**
     * @param item This method will convert the given item to ThongTinNoiThat
     * @return
     */
    public static ThongTinNoiThat convertFromTreeItem(TreeItem<BangNoiThat> item) {
        return new ThongTinNoiThat(item.getValue());
    }

    /**
     * @param thongTinNoiThat This method will convert the given ThongTinNoiThat to TreeItem
     * @return
     */
    public static TreeItem<BangNoiThat> convertToTreeItem(ThongTinNoiThat thongTinNoiThat) {
        return TableUtils.createNewItem(new BangNoiThat(thongTinNoiThat));
    }
}
