package com.huy.appnoithat.Controller.LuaChonNoiThat.Common;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;

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
        TreeItem<BangNoiThat> newItem = new TreeItem<>(new BangNoiThat(id, 0.0, 0.0, 0.0, 0L,
                "", "", "", 0L, 0.0));
        newItem.addEventHandler(TreeItem.branchCollapsedEvent(),
                (EventHandler<TreeItem.TreeModificationEvent<String>>) event -> event.getTreeItem().setExpanded(true));
        newItem.setExpanded(true);
        return newItem;
    }
    public static TreeItem<BangNoiThat> createRootItem(String id, TreeTableView<BangNoiThat> bangNoiThat, TableView<BangThanhToan> bangThanhToan) {
        TreeItem<BangNoiThat> newItem = createNewItem(id);
        newItem.getValue().getThanhTien().addListener((observableValue, aLong, t1) -> {
            TableCalculationUtils.calculateBangThanhToan(bangThanhToan, t1.longValue());
        });
        newItem.addEventHandler(TreeItem.childrenModificationEvent(), event -> {
//            System.out.println("new item added");
            bangNoiThat.scrollTo(9999);
        });
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
    public static byte[] readFromImage(Image img) {
        int w = (int)img.getWidth();
        int h = (int)img.getHeight();
        byte[] buf = new byte[w * h * 4];
        img.getPixelReader().getPixels(0, 0, w, h, PixelFormat.getByteBgraInstance(), buf, 0, w * 4);
        return buf;
    }
}
