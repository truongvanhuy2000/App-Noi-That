package com.huy.appnoithat.DataModel;

import lombok.Data;

import java.io.InputStream;

@Data
public class ThongTinCongTy {
    InputStream logo;
    String tenCongTy;
    String diaChiVanPhong;
    String diaChiXuong;
    String soDienThoai;
    String email;

    public ThongTinCongTy(InputStream logo, String tenCongTy, String diaChiVanPhong, String diaChiXuong, String soDienThoai, String email) {
        this.logo = logo;
        this.tenCongTy = tenCongTy;
        this.diaChiVanPhong = "Địa chỉ văn phòng: " + diaChiVanPhong;
        this.diaChiXuong = "Địa chỉ nhà xưởng: " + diaChiXuong;
        this.soDienThoai = "Hotline: " + soDienThoai;
        this.email = "Email: " + email;
    }
}
