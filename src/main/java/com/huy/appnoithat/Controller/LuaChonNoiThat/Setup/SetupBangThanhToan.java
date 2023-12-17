package com.huy.appnoithat.Controller.LuaChonNoiThat.Setup;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.CustomConverter.CustomLongStringConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;

public class SetupBangThanhToan {

    private final TableView<BangThanhToan> bangThanhToan;
    private final TableColumn<BangThanhToan, Long> DatCocThiCong30, DatCocThietKe10, HangDenChanCongTrinh50, NghiemThuQuyet, TongTien;
    private List<Integer> percentageList;

    public SetupBangThanhToan(LuaChonNoiThatController luaChonNoiThatController) {
        bangThanhToan = luaChonNoiThatController.getBangThanhToan();
        DatCocThiCong30 = luaChonNoiThatController.getDatCocThiCong30();
        DatCocThietKe10 = luaChonNoiThatController.getDatCocThietKe10();
        HangDenChanCongTrinh50 = luaChonNoiThatController.getHangDenChanCongTrinh50();
        NghiemThuQuyet = luaChonNoiThatController.getNghiemThuQuyet();
        TongTien = luaChonNoiThatController.getTongTien();
        percentageList = luaChonNoiThatController.getPercentageList();
    }

    /**
     * Set up the BangThanhToan table with custom cell factories and cell value factories.
     * Also sets up the row factory to make the rows bold.
     */
    public void setUpBangThanhToan() {
        bangThanhToan.setEditable(true);

        DatCocThietKe10.setCellValueFactory(param -> param.getValue().getDatCocThietKe10().asObject());
        DatCocThietKe10.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));
        DatCocThietKe10.getContextMenu().getItems().get(0).setOnAction((event) -> {
            String newPercentage = PopupUtils.openDialog("Thay đổi phần trăm", "", "Phần trăm");
            if (newPercentage != null) {
                percentageList.set(0, Integer.parseInt(newPercentage));
                DatCocThietKe10.setText(String.format("Đặt cọc thiết kế: %s", newPercentage + "%"));
            }
        });

        DatCocThiCong30.setCellValueFactory(param -> param.getValue().getDatCocThiCong30().asObject());
        DatCocThiCong30.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));
        DatCocThiCong30.getContextMenu().getItems().get(0).setOnAction((event) -> {
            String newPercentage = PopupUtils.openDialog("Thay đổi phần trăm", "", "Phần trăm");
            if (newPercentage != null) {
                percentageList.set(1, Integer.parseInt(newPercentage));
                DatCocThiCong30.setText(String.format("Đặt cọc thi công: %s", newPercentage + "%"));
            }
        });

        HangDenChanCongTrinh50.setCellValueFactory(param -> param.getValue().getHangDenChanCongTrinh50().asObject());
        HangDenChanCongTrinh50.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));
        HangDenChanCongTrinh50.getContextMenu().getItems().get(0).setOnAction((event) -> {
            String newPercentage = PopupUtils.openDialog("Thay đổi phần trăm", "", "Phần trăm");
            if (newPercentage != null) {
                percentageList.set(2, Integer.parseInt(newPercentage));
                HangDenChanCongTrinh50.setText(String.format("Đặt cọc thi công: %s", newPercentage + "%"));
            }
        });

        NghiemThuQuyet.setCellValueFactory(param -> param.getValue().getNghiemThuQuyet().asObject());
        NghiemThuQuyet.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        TongTien.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));
        TongTien.setCellValueFactory(param -> param.getValue().getTongTien().asObject());
        TongTien.setEditable(false);
        bangThanhToan.setRowFactory(new Callback<>() {
            @Override
            public TableRow<BangThanhToan> call(TableView<BangThanhToan> param) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(BangThanhToan row1, boolean empty) {
                        super.updateItem(row1, empty);
                        setStyle("-fx-font-weight: bold; -fx-font-size: 14px");
                    }
                };
            }
        });
        if (bangThanhToan.getItems().isEmpty()) {
            bangThanhToan.setItems(FXCollections.observableArrayList(
                    new BangThanhToan(0L, 0L, 0L, 0L, 0L)
            ));
        }
    }
}
