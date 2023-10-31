package com.huy.appnoithat.Controller.LuaChonNoiThat.Setup;

import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SetupTruongThongTin {
    private TextField TenCongTy, VanPhong, DiaChiXuong, DienThoaiCongTy, Email;
    private TextField TenKhachHang, DienThoaiKhachHang, DiaChiKhachHang, NgayLapBaoGia, SanPham;
    private TextArea noteArea;

    public SetupTruongThongTin(LuaChonNoiThatController luaChonNoiThatController) {
        TenCongTy = luaChonNoiThatController.getTenCongTy();
        VanPhong = luaChonNoiThatController.getVanPhong();
        DiaChiXuong = luaChonNoiThatController.getDiaChiXuong();
        DienThoaiCongTy = luaChonNoiThatController.getDienThoaiCongTy();
        Email = luaChonNoiThatController.getEmail();
        TenKhachHang = luaChonNoiThatController.getTenKhachHang();
        DienThoaiKhachHang = luaChonNoiThatController.getDienThoaiKhachHang();
        DiaChiKhachHang = luaChonNoiThatController.getDiaChiKhachHang();
        NgayLapBaoGia = luaChonNoiThatController.getNgayLapBaoGia();
        SanPham = luaChonNoiThatController.getSanPham();
        noteArea = luaChonNoiThatController.getNoteTextArea();
    }

    public void setup() {
        noteArea.setPrefHeight(30);
        noteArea.setMaxHeight(30);
        noteArea.setOnMouseClicked(mouseEvent -> {
            noteArea.setMaxHeight(400);
            Platform.runLater(() -> noteArea.setPrefHeight(400));
//            noteArea.setPrefWidth(200);
        });
        noteArea.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!noteArea.isFocused()) {
                System.out.println("Focus lost");
                noteArea.setPrefHeight(30);
                noteArea.setMaxHeight(30);
            }
        });
    }
}
