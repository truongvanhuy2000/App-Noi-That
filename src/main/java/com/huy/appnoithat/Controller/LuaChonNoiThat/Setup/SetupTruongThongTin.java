package com.huy.appnoithat.Controller.LuaChonNoiThat.Setup;

import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import javafx.scene.control.TextField;

public class SetupTruongThongTin {
    private TextField TenCongTy, VanPhong, DiaChiXuong, DienThoaiCongTy, Email;
    private TextField TenKhachHang, DienThoaiKhachHang, DiaChiKhachHang, NgayLapBaoGia, SanPham;
    private PersistenceStorageService persistenceStorageService;

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

        persistenceStorageService = PersistenceStorageService.getInstance();
    }

    public void setup() {
        TenCongTy.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("TAB") || event.getCode().toString().equals("ENTER")) {
                ThongTinCongTy thongTinCongTy = persistenceStorageService.getThongTinCongTy();
                thongTinCongTy.setTenCongTy(TenCongTy.getText());
                persistenceStorageService.setThongTinCongTy(thongTinCongTy);
            }
        });
        VanPhong.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("TAB") || event.getCode().toString().equals("ENTER")) {
                ThongTinCongTy thongTinCongTy = persistenceStorageService.getThongTinCongTy();
                thongTinCongTy.setDiaChiVanPhong(VanPhong.getText());
                persistenceStorageService.setThongTinCongTy(thongTinCongTy);
            }
        });
        DiaChiXuong.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("TAB") || event.getCode().toString().equals("ENTER")) {
                ThongTinCongTy thongTinCongTy = persistenceStorageService.getThongTinCongTy();
                thongTinCongTy.setDiaChiXuong(DiaChiXuong.getText());
                persistenceStorageService.setThongTinCongTy(thongTinCongTy);
            }
        });
        DienThoaiCongTy.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("TAB") || event.getCode().toString().equals("ENTER")) {
                ThongTinCongTy thongTinCongTy = persistenceStorageService.getThongTinCongTy();
                thongTinCongTy.setSoDienThoai(DienThoaiCongTy.getText());
                persistenceStorageService.setThongTinCongTy(thongTinCongTy);
            }
        });
        Email.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("TAB") || event.getCode().toString().equals("ENTER")) {
                ThongTinCongTy thongTinCongTy = persistenceStorageService.getThongTinCongTy();
                thongTinCongTy.setEmail(Email.getText());
                persistenceStorageService.setThongTinCongTy(thongTinCongTy);
            }
        });
    }
}
