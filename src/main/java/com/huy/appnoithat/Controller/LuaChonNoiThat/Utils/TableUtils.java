package com.huy.appnoithat.Controller.LuaChonNoiThat.Utils;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.NoiThatItem;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class TableUtils {
    private static final Logger LOGGER = LogManager.getLogger(TableUtils.class);
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
    public static boolean isAllowedToEdit(TreeItem<BangNoiThat> item) {
        return item.getValue().getItemType() == ItemType.NUMERIC;
    }

    /**
     * @param TableNoiThat This method will check if the current cell is editable
     * @return
     */
    public static boolean isEditable(TreeTableView<BangNoiThat> TableNoiThat) {
        TreeItem<BangNoiThat> selectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return false;
        }
        return isAllowedToEdit(selectedItem);
    }

    public static TreeItem<BangNoiThat> createNewItem(ItemType type, String id) {
        BangNoiThat bangNoiThat = BangNoiThat.builder()
                .id(id).itemType(type).rong(0.0).dai(0.0).cao(0.0).donVi("")
                .donGia(0L).khoiLuong(0.0).thanhTien(0L)
                .build();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(bangNoiThat);
        setupNewItem(newItem);
        return newItem;
    }

    public static TreeItem<BangNoiThat> createNewItem(ItemType type, String id, NoiThatItem noiThatItem) {
        BangNoiThat bangNoiThat = BangNoiThat.builder()
                .id(id).itemType(type).rong(0.0).dai(0.0).cao(0.0).donVi("").hangMuc(noiThatItem)
                .donGia(0L).khoiLuong(0.0).thanhTien(0L)
                .build();
        TreeItem<BangNoiThat> newItem = new TreeItem<>(bangNoiThat);
        setupNewItem(newItem);
        return newItem;
    }

    public static TreeItem<BangNoiThat> createRootItem(ItemType type, TreeTableView<BangNoiThat> bangNoiThat,
                                                       TableView<BangThanhToan> bangThanhToan) {
        TreeItem<BangNoiThat> newItem = createNewItem(type, "0");
        newItem.getValue().getThanhTien().addListener((observableValue, aLong, t1) -> {
            TableCalculationUtils.calculateBangThanhToan(bangThanhToan, t1.longValue());
        });
        newItem.addEventHandler(TreeItem.childrenModificationEvent(), event -> {
            if (bangNoiThat.getSelectionModel().getSelectedCells().isEmpty()) {
                return;
            }
            int row = bangNoiThat.getSelectionModel().getSelectedCells().get(0).getRow();
            Platform.runLater(() -> {
                bangNoiThat.scrollTo(row);
            });
        });
        return newItem;
    }

    /**
     * @param bangNoiThat This method will create a new item with the given BangNoiThat
     * @return
     */
    public static TreeItem<BangNoiThat> createNewItem(BangNoiThat bangNoiThat) {
        BangNoiThat newBangNoiThat = new BangNoiThat(bangNoiThat);
        TreeItem<BangNoiThat> newItem = new TreeItem<>(newBangNoiThat);
        setupNewItem(newItem);
        return newItem;
    }

    private static void setupNewItem(TreeItem<BangNoiThat> newItem) {
        newItem.addEventHandler(TreeItem.branchCollapsedEvent(),
                (EventHandler<TreeItem.TreeModificationEvent<String>>) event -> event.getTreeItem().setExpanded(true));
        newItem.setExpanded(true);
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

    public static void reArrangeList(TreeTableView<BangNoiThat> TableNoiThat) {
        ObservableList<TreeItem<BangNoiThat>> listItem = TableNoiThat.getRoot().getChildren();
        for (int i = 0; i < listItem.size(); i++) {
            TreeItem<BangNoiThat> item1 = listItem.get(i);
            for (int j = 0; j < item1.getChildren().size(); j++) {
                TreeItem<BangNoiThat> item2 = item1.getChildren().get(j);
                for (int z = 0; z < item2.getChildren().size(); z++) {
                    TreeItem<BangNoiThat> item3 = item2.getChildren().get(z);
                    item3.getValue().setSTT(String.valueOf(z + 1));
                }
                item2.getValue().setSTT(Utils.toRoman(j + 1));
            }
            item1.getValue().setSTT(Utils.toAlpha(i + 1));
        }
    }

    /**
     * Checks if adding a new child item to the given root TreeItem would exceed the maximum allowed limit of 30 children.
     * If the limit is reached, displays an error message and returns true; otherwise, returns false.
     *
     * @param root The parent TreeItem to which a new child item is to be added.
     * @return True if the limit is reached, false otherwise.
     */
    public static Optional<TreeItem<BangNoiThat>> createNewSibling(TreeItem<BangNoiThat> currentItem) {
        if (currentItem == null) {
            LOGGER.error("Current item must not be null");
            throw new IllegalArgumentException();
        }
        BangNoiThat bangNoiThat = currentItem.getValue();
        String nextStt = ItemTypeUtils.findTheNextStt(bangNoiThat);
        TreeItem<BangNoiThat> newItem = TableUtils.createNewItem(bangNoiThat.getItemType(), nextStt);
        return createNewSibling(currentItem, newItem);
    }

    public static Optional<TreeItem<BangNoiThat>> createNewSibling(TreeItem<BangNoiThat> currentItem, TreeItem<BangNoiThat> newItem) {
        if (currentItem == null || newItem == null) {
            LOGGER.error("Current item and newItem must not be null");
            throw new IllegalArgumentException();
        }
        if (currentItem.getParent() == null) {
            LOGGER.error("Current item parent not be null");
            return Optional.empty();
        }
        int currentPos = currentItem.getParent().getChildren().indexOf(currentItem);
        if (currentPos != -1) {
            currentItem.getParent().getChildren().add(currentPos + 1, newItem);
        }
        return Optional.of(newItem);
    }
}
