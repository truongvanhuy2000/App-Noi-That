package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.InputStream;

@Data
public class ThongTinCongTy {
    @JsonIgnore
    InputStream logo;
    @JsonProperty("tenCongTy")
    String tenCongTy;
    @JsonProperty("diaChiVanPhong")
    String diaChiVanPhong;
    @JsonProperty("diaChiXuong")
    String diaChiXuong;
    @JsonProperty("soDienThoai")
    String soDienThoai;
    @JsonProperty("email")
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
