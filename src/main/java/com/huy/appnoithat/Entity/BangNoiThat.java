package com.huy.appnoithat.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BangNoiThat {
    private int id;
    private String PhongCach;
    private float Cao;
    private float Dai;
    private float Rong;
    private long DonGia;
    private String DonVi;
    private String HangMuc;
    private String NoiThat;
    private String VatLieu;
    private long ThanhTien;
    private float SoLuong;
}