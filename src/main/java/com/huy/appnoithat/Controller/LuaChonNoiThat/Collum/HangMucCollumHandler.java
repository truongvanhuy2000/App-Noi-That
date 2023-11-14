package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomHangMucCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.ItemTypeUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HangMucCollumHandler {
    private final ObservableList<String> hangMucList;
    private final LuaChonNoiThatService luaChonNoiThatService;
    final static Logger LOGGER = LogManager.getLogger(HangMucCollumHandler.class);

    /**
     * Constructs a HangMucCollumHandler with the given ObservableList of hangMucList.
     *
     * @param hangMucList The ObservableList of String representing hangMucList data.
     */
    public HangMucCollumHandler(ObservableList<String> hangMucList) {
        this.hangMucList = hangMucList;
        luaChonNoiThatService = new LuaChonNoiThatService();
    }

    /**
     * Handles cell edit commit events for the HangMuc column in the TreeTableView.
     * Updates the hangMuc property of the corresponding BangNoiThat object with the new value.
     *
     * @param event The CellEditEvent containing information about the edit event.
     */
    public void onEditCommitHangMuc(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> rowValue = event.getRowValue();
        if (rowValue != null) {
            String stt = rowValue.getValue().getSTT().getValue();
            String newValue = event.getNewValue();
            rowValue.getValue().setHangMuc(newValue);
            if (ItemTypeUtils.determineItemType(stt) == ItemType.ROMAN) {
                automaticallyInsertNoiThat(event);
            }
            if (ItemTypeUtils.determineItemType(stt) == ItemType.NUMERIC) {
                automaticallyInsertVatLieuAndThongSo(event);
            }
        }
    }

    private void automaticallyInsertNoiThat(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> rowValue = event.getRowValue();
        if (rowValue == null) {
            LOGGER.error("Null row value when automatically insert vat lieu and thong so");
            return;
        }
        String noiThat = rowValue.getValue().getHangMuc().getValue();
        String phongCach = rowValue.getParent().getValue().getHangMuc().getValue();

        List<HangMuc> items = luaChonNoiThatService.findHangMucListBy(phongCach, noiThat);
        if (items == null || items.isEmpty()) {
            LOGGER.error("cant found nothing");
            return;
        }
        rowValue.getChildren().clear();
        int index = 1;
        for (HangMuc item : items) {
            TreeItem<BangNoiThat> newItem = TableUtils.createNewItem(ItemType.NUMERIC, String.valueOf(index++));
            rowValue.getChildren().add(newItem);
            newItem.getValue().setHangMuc(item.getName());
            automaticallyInsertVatLieuAndThongSo(newItem);
        }
        TableCalculationUtils.recalculateAllTongTien(event.getTreeTableView());
    }

    private void automaticallyInsertVatLieuAndThongSo(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> rowValue = event.getRowValue();
        if (rowValue == null) {
            LOGGER.error("Null row value when automatically insert vat lieu and thong so");
            return;
        }
        automaticallyInsertVatLieuAndThongSo(rowValue);
        TableCalculationUtils.recalculateAllTongTien(event.getTreeTableView());
        event.getTreeTableView().edit(event.getTreeTablePosition().getRow(), event.getTreeTableView().getVisibleLeafColumn(2));

    }
    private void automaticallyInsertVatLieuAndThongSo(TreeItem<BangNoiThat> rowValue) {
        Optional<VatLieu> vatLieu = getTheFirstVatLieu(rowValue);
        if (vatLieu.isPresent()) {
            String firstVatLieu = vatLieu.get().getName();
            ThongSo thongSo = vatLieu.get().getThongSo();
            if (thongSo != null) {
                Double dai = Objects.requireNonNullElse(thongSo.getDai(), 0.0);
                Double rong = Objects.requireNonNullElse(thongSo.getRong(), 0.0);
                Double cao = Objects.requireNonNullElse(thongSo.getCao(), 0.0);
                Long donGia = thongSo.getDon_gia();
                String donVi = thongSo.getDon_vi();
                Double khoiLuong = TableCalculationUtils.calculateKhoiLuong(dai, cao, rong, donVi);
                Long thanhTien = TableCalculationUtils.calculateThanhTien(khoiLuong, donGia);

                rowValue.getValue().setThanhTien(thanhTien);
                rowValue.getValue().setKhoiLuong(khoiLuong);
                rowValue.getValue().setDai(dai);
                rowValue.getValue().setRong(rong);
                rowValue.getValue().setCao(cao);
                rowValue.getValue().setDonGia(donGia);
                rowValue.getValue().setDonVi(donVi);
            }
            rowValue.getValue().setVatLieu(firstVatLieu);
        }
    }
    private Optional<VatLieu> getTheFirstVatLieu(TreeItem<BangNoiThat> currentItem) {
        try {
            String noiThat = currentItem.getParent().getValue().getHangMuc().getValue();
            String phongCach = currentItem.getParent().getParent().getValue().getHangMuc().getValue();
            String hangMuc = currentItem.getValue().getHangMuc().getValue();
            List<VatLieu> items = luaChonNoiThatService.findVatLieuListBy(phongCach, noiThat, hangMuc);
            if (items.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(items.get(0));
        } catch (NullPointerException e) {
            PopupUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
            LOGGER.error("Error when get the first vat lieu item");
            return Optional.empty();
        }
    }
    /**
     * Handles the event when a cell in the 'HangMuc' TreeTableColumn is being edited.
     * Dynamically populates 'hangMucList' based on the type of the edited item (noi that, phong cach, or hang muc).
     *
     * @param event The CellEditEvent containing information about the editing event.
     */
    public void onStartEditHangMuc(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        String stt = currentItem.getValue().getSTT().getValue();
        List<String> items;
        hangMucList.clear();
        // Roman mean it's a noi that, mean that its parent is phong cach
        switch (ItemTypeUtils.determineItemType(currentItem)) {
            case ROMAN -> {
                String phongCach = currentItem.getParent().getValue().getHangMuc().getValue();
                try {
                    items = Utils.getObjectNameList(luaChonNoiThatService.findNoiThatListBy(phongCach));
                } catch (NullPointerException e) {
                    PopupUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
                    return;
                }
                hangMucList.addAll(items);
            }
            case AlPHA -> {
                try {
                    items = Utils.getObjectNameList(luaChonNoiThatService.findAllPhongCachNoiThat());
                } catch (NullPointerException e) {
                    PopupUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
                    return;
                }
                hangMucList.clear();
                hangMucList.addAll(items);
            }
            case NUMERIC -> {
                String noiThat = currentItem.getParent().getValue().getHangMuc().getValue();
                String phongCach = currentItem.getParent().getParent().getValue().getHangMuc().getValue();
                try {
                    items = Utils.getObjectNameList(luaChonNoiThatService.findHangMucListBy(phongCach, noiThat));
                } catch (NullPointerException e) {
                    PopupUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
                    return;
                }
                hangMucList.clear();
                hangMucList.addAll(items);
            }
            default -> {
            }
        }
    }

    /**
     * Provides a custom cell factory for the HangMuc column in the TreeTableView.
     * Initializes and returns a new instance of CustomHangMucCell with the specified 'hangMucList'.
     *
     * @param param The TreeTableColumn instance for which the custom cell factory is provided.
     * @return A new CustomHangMucCell instance with the given 'hangMucList'.
     */
    public TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param) {
        return new CustomHangMucCell(hangMucList);
    }


    /**
     * Provides a custom cell value factory for the HangMuc column in the TreeTableView.
     * Retrieves the 'hangMuc' property value from the BangNoiThat object associated with the current row.
     * Returns an ObservableValue<String> representing the 'hangMuc' property of the cell data features.
     *
     * @param param The CellDataFeatures instance representing the data for the current cell.
     * @return An ObservableValue<String> representing the 'hangMuc' property of the current cell's data.
     *         Returns null if the current row's data is null.
     */
    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param) {
        if (param.getValue() == null) {
            return null;
        }
        return param.getValue().getValue().getHangMuc();
    }
}
