package com.huy.appnoithat.Controller.LuaChonNoiThat.Setup;

import com.huy.appnoithat.Controller.LuaChonNoiThat.CustomConverter.CustomLongStringConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class SetupBangThanhToan {
    private TableView<BangThanhToan> bangThanhToan;
    private TableColumn<BangThanhToan, Long> DatCocThiCong30, DatCocThietKe10, HangDenChanCongTrinh50, NghiemThuQuyet;

    public SetupBangThanhToan(LuaChonNoiThatController luaChonNoiThatController) {
        bangThanhToan = luaChonNoiThatController.getBangThanhToan();
        DatCocThiCong30 = luaChonNoiThatController.getDatCocThiCong30();
        DatCocThietKe10 = luaChonNoiThatController.getDatCocThietKe10();
        HangDenChanCongTrinh50 = luaChonNoiThatController.getHangDenChanCongTrinh50();
        NghiemThuQuyet = luaChonNoiThatController.getNghiemThuQuyet();
    }

    public void setUpBangThanhToan() {
        DatCocThietKe10.setCellValueFactory(param -> param.getValue().getDatCocThietKe10().asObject());
        DatCocThietKe10.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        DatCocThiCong30.setCellValueFactory(param -> param.getValue().getDatCocThiCong30().asObject());
        DatCocThiCong30.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        HangDenChanCongTrinh50.setCellValueFactory(param -> param.getValue().getHangDenChanCongTrinh50().asObject());
        HangDenChanCongTrinh50.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        NghiemThuQuyet.setCellValueFactory(param -> param.getValue().getNghiemThuQuyet().asObject());
        NghiemThuQuyet.setCellFactory(param -> new TextFieldTableCell<>(new CustomLongStringConverter()));

        if (bangThanhToan.getItems().isEmpty()) {
            bangThanhToan.setItems(FXCollections.observableArrayList(
                    new BangThanhToan(0L, 0L, 0L, 0L)
            ));
        }

    }
}
