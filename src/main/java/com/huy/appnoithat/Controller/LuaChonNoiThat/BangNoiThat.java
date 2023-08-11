package com.huy.appnoithat.Controller.LuaChonNoiThat;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BangNoiThat {
    private SimpleIntegerProperty STT;
    private SimpleFloatProperty Cao;
    private SimpleFloatProperty Dai;
    private SimpleFloatProperty Rong;
    private SimpleLongProperty DonGia;
    private SimpleStringProperty DonVi;
    private SimpleStringProperty HangMuc;
    private SimpleStringProperty VatLieu;
    private SimpleLongProperty ThanhTien;
    private SimpleFloatProperty KhoiLuong;

    public BangNoiThat(int id, Float cao, Float dai, Float rong, Long donGia, String donVi, String hangMuc, String vatLieu, Long thanhTien, Float khoiLuong) {
        STT = new SimpleIntegerProperty(id);
        Cao = new SimpleFloatProperty(cao);
        Dai = new SimpleFloatProperty(dai);
        Rong = new SimpleFloatProperty(rong);
        DonGia = new SimpleLongProperty(donGia);
        DonVi = new SimpleStringProperty(donVi);
        HangMuc = new SimpleStringProperty(hangMuc);
        VatLieu = new SimpleStringProperty(vatLieu);
        ThanhTien = new SimpleLongProperty(thanhTien);
        KhoiLuong = new SimpleFloatProperty(khoiLuong);
    }
}
