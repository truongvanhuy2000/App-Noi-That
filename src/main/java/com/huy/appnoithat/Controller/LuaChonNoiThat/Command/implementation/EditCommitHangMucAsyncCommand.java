package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.NoiThatItem;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Utils.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Utils.TableUtils;
import com.huy.appnoithat.DataModel.Entity.HangMuc;
import com.huy.appnoithat.DataModel.Entity.ThongSo;
import com.huy.appnoithat.DataModel.Entity.VatLieu;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

@RequiredArgsConstructor
public class EditCommitHangMucAsyncCommand implements Command {
    private final Logger LOGGER = LogManager.getLogger(this);
    private final LuaChonNoiThatService luaChonNoiThatService;
    private final TreeTableColumn.CellEditEvent<BangNoiThat, NoiThatItem> event;

    private TreeItem<BangNoiThat> rowValue;
    private TreeTableView<BangNoiThat> treeTableView;
    private Memento bangNoiThatSnapshot;
    private final ObservableList<TreeItem<BangNoiThat>> oldChildren = FXCollections.observableArrayList();
    private final CompletionService<Void> completionService = new ExecutorCompletionService<>(Executors.newFixedThreadPool(4));

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

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (HangMuc item : items) {
            var noiThatItem = new NoiThatItem(item.getId(), item.getName());
            TreeItem<BangNoiThat> newItem = TableUtils.createNewItem(ItemType.NUMERIC, String.valueOf(index++));
            rowValue.getChildren().add(newItem);
            newItem.getValue().setHangMuc(noiThatItem);
            futures.add(populateHangMucChild(newItem));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenAccept((result) -> {
            Platform.runLater(() -> TableCalculationUtils.recalculateAllTongTien(treeTableView));
        });
    }

    private CompletableFuture<Void> populateHangMucChild(TreeItem<BangNoiThat> item) {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                automaticallyInsertVatLieuAndThongSo(item);
                completableFuture.complete(null);
                return null;
            }
        };
        completionService.submit(task, null);
        return completableFuture;
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
        BangNoiThat bangNoiThat = rowValue.getValue();
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

                bangNoiThat.setThanhTien(thanhTien);
                bangNoiThat.setKhoiLuong(khoiLuong);
                bangNoiThat.setDai(dai);
                bangNoiThat.setRong(rong);
                bangNoiThat.setCao(cao);
                bangNoiThat.setDonGia(donGia);
                bangNoiThat.setDonVi(donVi);
            }
            bangNoiThat.setVatLieu(firstVatLieu);
            Platform.runLater(() -> {
                rowValue.setValue(bangNoiThat);
            });

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
