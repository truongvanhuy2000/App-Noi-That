package com.huy.appnoithat.DataModel;

import lombok.Data;

@Data
public class ThongTinKhachHang {
    String tenKhachHang;
    String diaChi;
    String soDienThoai;
    String date;
    String SanPham;

    public ThongTinKhachHang(String tenKhachHang, String diaChi, String soDienThoai, String date, String sanPham) {
        this.tenKhachHang = "Khách hàng : " + tenKhachHang;
        this.diaChi = "Địa chỉ: " + diaChi;
        this.soDienThoai = "Điện thoại: " + soDienThoai;
        this.date = "Ngày: " + date;
        SanPham = "Sản phẩm: " + sanPham;
    }

}
