package com.huy.appnoithat.Controller.LuaChonNoiThat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BangNoiThat {
    private int id;
    private String PhongCach;
    private Float Cao;
    private Float Dai;
    private Float Rong;
    private Long DonGia;
    private String DonVi;
    private String HangMuc;
    private String NoiThat;
    private String VatLieu;
    private Long ThanhTien;
    private Float SoLuong;
}
