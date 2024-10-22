package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.ItemTypeUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.NoiThatItem;
import com.huy.appnoithat.DataModel.Entity.HangMuc;
import com.huy.appnoithat.DataModel.Entity.ThongSo;
import com.huy.appnoithat.DataModel.Entity.VatLieu;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class EditCommitHangMucCommand implements Command {
    private final Logger LOGGER = LogManager.getLogger(this);
    private final LuaChonNoiThatService luaChonNoiThatService;
    private final TreeTableColumn.CellEditEvent<BangNoiThat, NoiThatItem> event;

    private TreeItem<BangNoiThat> rowValue;
    private TreeTableView<BangNoiThat> treeTableView;
    private Memento bangNoiThatSnapshot;
    private final ObservableList<TreeItem<BangNoiThat>> oldChildren = FXCollections.observableArrayList();

    @Override
    public void execute() {
        rowValue = event.getRowValue();
        treeTableView = event.getTreeTableView();
        if (rowValue == null) {
            return;
        }
        BangNoiThat bangNoiThat = rowValue.getValue();
        bangNoiThatSnapshot = bangNoiThat.createSnapshot();
        oldChildren.addAll(rowValue.getChildren());

        NoiThatItem newValue = event.getNewValue();
        bangNoiThat.setHangMuc(newValue);
        if (bangNoiThat.getItemType() == ItemType.ROMAN) {
            automaticallyInsertNoiThat(event);
        }
        if (bangNoiThat.getItemType() == ItemType.NUMERIC) {
            automaticallyInsertVatLieuAndThongSo(event);
        }
    }

    @Override
    public void undo() {
        if (bangNoiThatSnapshot == null) {
            return;
        }
        bangNoiThatSnapshot.restore();
        rowValue.getChildren().clear();
        rowValue.getChildren().addAll(oldChildren);

        TableCalculationUtils.recalculateAllTongTien(treeTableView);
    }

    private void automaticallyInsertNoiThat(TreeTableColumn.CellEditEvent<BangNoiThat, NoiThatItem> event) {
        TreeItem<BangNoiThat> rowValue = event.getRowValue();
        if (rowValue == null) {
            LOGGER.error("Null row value when automatically insert vat lieu and thong so");
            return;
        }
        NoiThatItem noiThat = rowValue.getValue().getHangMuc().getValue();

        List<HangMuc> items = luaChonNoiThatService.findHangMucListByNoiThatID(noiThat.getId());
        if (items == null || items.isEmpty()) {
            LOGGER.error("cant found nothing");
            return;
        }
        rowValue.getChildren().clear();
        int index = 1;
        for (HangMuc item : items) {
            var noiThatItem = new NoiThatItem(item.getId(), item.getName());
            TreeItem<BangNoiThat> newItem = TableUtils.createNewItem(ItemType.NUMERIC, String.valueOf(index++));
            rowValue.getChildren().add(newItem);
            newItem.getValue().setHangMuc(noiThatItem);
            automaticallyInsertVatLieuAndThongSo(newItem);
        }
        TableCalculationUtils.recalculateAllTongTien(treeTableView);
    }

    private void automaticallyInsertVatLieuAndThongSo(TreeTableColumn.CellEditEvent<BangNoiThat, NoiThatItem> event) {
        TreeItem<BangNoiThat> rowValue = event.getRowValue();
        if (rowValue == null) {
            LOGGER.error("Null row value when automatically insert vat lieu and thong so");
            return;
        }
        automaticallyInsertVatLieuAndThongSo(rowValue);
        TableCalculationUtils.recalculateAllTongTien(treeTableView);
        event.getTreeTableView().edit(event.getTreeTablePosition().getRow(), event.getTreeTableView().getVisibleLeafColumn(2));

    }

    private void automaticallyInsertVatLieuAndThongSo(TreeItem<BangNoiThat> rowValue) {
        Optional<VatLieu> vatLieu = getTheFirstVatLieu(rowValue);
        if (vatLieu.isPresent()) {
            NoiThatItem firstVatLieu = new NoiThatItem(vatLieu.get().getId(), vatLieu.get().getName());
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
            NoiThatItem item = currentItem.getValue().getHangMuc().getValue();
            List<VatLieu> items = luaChonNoiThatService.findVatLieuListByHangMucID(item.getId());
            if (items.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(items.get(0));
        } catch (NullPointerException e) {
            PopupUtils.throwErrorNotification("Chưa lựa chọn thông tin phía trên");
            LOGGER.error("Error when get the first vat lieu item");
            return Optional.empty();
        }
    }
}
