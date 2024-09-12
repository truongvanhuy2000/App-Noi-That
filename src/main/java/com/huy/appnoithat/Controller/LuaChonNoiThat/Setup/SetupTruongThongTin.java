package com.huy.appnoithat.Controller.LuaChonNoiThat.Setup;

import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SetupTruongThongTin {
    private final TextField TenCongTy;
    private final TextField VanPhong;
    private final TextField DiaChiXuong;
    private final TextField DienThoaiCongTy;
    private final TextField Email;
    private final TextField TenKhachHang;
    private final TextField DienThoaiKhachHang;
    private final TextField DiaChiKhachHang;
    private final TextField NgayLapBaoGia;
    private final TextField SanPham;
    private final TextArea noteArea;

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
        noteArea.setMinHeight(30);
        noteArea.setOnMouseClicked(mouseEvent -> {
            noteArea.setMaxHeight(400);
            Platform.runLater(() -> noteArea.setPrefHeight(400));
        });
        noteArea.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!noteArea.isFocused()) {
                noteArea.setPrefHeight(30);
                noteArea.setMaxHeight(30);
            }
        });
    }
}
