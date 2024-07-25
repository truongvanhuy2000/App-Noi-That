package com.huy.appnoithat.Controller.LuaChonNoiThat.Setup;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.CustomConverter.CustomLongStringConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class SetupBangThanhToan {

    private final TableView<BangThanhToan> bangThanhToan;
    private final TableColumn<BangThanhToan, Long> DatCocThiCong30, DatCocThietKe10, HangDenChanCongTrinh50, NghiemThuQuyet, TongTien;
    private final ObservableList<Integer> percentageList;

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
        MenuItem DatCocThietKePercent = new MenuItem("Thay đổi phần trăm đặt cọc thiết kế");
        DatCocThietKePercent.setOnAction((event) -> {
            String newPercentage = PopupUtils.openDialog("Thay đổi phần trăm", "", "Phần trăm");
            if (newPercentage != null) {
                percentageList.set(0, Integer.parseInt(newPercentage));
                percentageList.set(1, 100 - percentageList.get(0) - percentageList.get(2));
            }
        });
        MenuItem DatCocThiCongPercent = new MenuItem("Thay đổi phần trăm đặt cọc thi công");
        DatCocThiCongPercent.setOnAction((event) -> {
            String newPercentage = PopupUtils.openDialog("Thay đổi phần trăm", "", "Phần trăm");
            if (newPercentage != null) {
                percentageList.set(1, Integer.parseInt(newPercentage));
                percentageList.set(2, 100 - percentageList.get(0) - percentageList.get(1));
            }
        });
        MenuItem HangDenChanCongTrinhPercent = new MenuItem("Thay đổi phần trăm hàng đến chân công trình");
        HangDenChanCongTrinhPercent.setOnAction((event) -> {
            String newPercentage = PopupUtils.openDialog("Thay đổi phần trăm", "", "Phần trăm");
            if (newPercentage != null) {
                percentageList.set(2, Integer.parseInt(newPercentage));
                percentageList.set(0, 100 - percentageList.get(1) - percentageList.get(2));
            }
        });
        MenuItem recalculate = new MenuItem("Tính toán lại");
        recalculate.setOnAction((event) -> {
            updateBangThanhToan();
        });
        bangThanhToan.getContextMenu().getItems().addAll(DatCocThietKePercent, DatCocThiCongPercent, HangDenChanCongTrinhPercent, recalculate);

        DatCocThietKe10.setCellValueFactory(param -> param.getValue().getDatCocThietKe10().asObject());
        DatCocThietKe10.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        DatCocThiCong30.setCellValueFactory(param -> param.getValue().getDatCocThiCong30().asObject());
        DatCocThiCong30.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        HangDenChanCongTrinh50.setCellValueFactory(param -> param.getValue().getHangDenChanCongTrinh50().asObject());
        HangDenChanCongTrinh50.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

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
        percentageList.addListener((ListChangeListener<? super Integer>) change -> {
            updateBangThanhToan();
        });
    }
    private void updateBangThanhToan() {
        DatCocThietKe10.setText(String.format("Đặt cọc thiết kế: %s", percentageList.get(0) + "%"));
        DatCocThiCong30.setText(String.format("Đặt cọc thi công: %s", percentageList.get(1) + "%"));
        HangDenChanCongTrinh50.setText(String.format("Đặt cọc thi công: %s", percentageList.get(2) + "%"));

        TableCalculationUtils.calculateBangThanhToan(bangThanhToan, TongTien.getCellData(0));
    }
}
