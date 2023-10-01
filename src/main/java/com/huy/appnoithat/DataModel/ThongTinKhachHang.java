package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ThongTinKhachHang {
    @JsonProperty("tenKhachHang")
    String tenKhachHang;
    @JsonProperty("diaChi")
    String diaChi;
    @JsonProperty("soDienThoai")
    String soDienThoai;
    @JsonProperty("date")
    String date;
    @JsonProperty("SanPham")
    String SanPham;

    public ThongTinKhachHang(String tenKhachHang, String diaChi, String soDienThoai, String date, String sanPham) {
        this.tenKhachHang = tenKhachHang;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.date = date;
        SanPham = sanPham;
    }
}
