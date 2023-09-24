package com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel;

import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BangNoiThat {
    private SimpleStringProperty STT;
    private SimpleFloatProperty Cao;
    private SimpleFloatProperty Dai;
    private SimpleFloatProperty Rong;
    private SimpleLongProperty DonGia;
    private SimpleStringProperty DonVi;
    private SimpleStringProperty HangMuc;
    private SimpleStringProperty VatLieu;
    private SimpleLongProperty ThanhTien;
    private SimpleFloatProperty KhoiLuong;

    public BangNoiThat(String id, Float cao, Float dai, Float rong, Long donGia, String donVi, String hangMuc, String vatLieu, Long thanhTien, Float khoiLuong) {
        STT = new SimpleStringProperty(id);
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

    public BangNoiThat(ThongTinNoiThat thongTinNoiThat) {
        this.STT = new SimpleStringProperty(thongTinNoiThat.getSTT());
        this.Cao = new SimpleFloatProperty(Float.parseFloat(thongTinNoiThat.getCao()));
        this.Dai = new SimpleFloatProperty(Float.parseFloat(thongTinNoiThat.getDai()));
        this.Rong = new SimpleFloatProperty(Float.parseFloat(thongTinNoiThat.getRong()));
        this.DonGia = new SimpleLongProperty(Long.parseLong(thongTinNoiThat.getDonGia()));
        this.DonVi = new SimpleStringProperty(thongTinNoiThat.getDonViTinh());
        this.HangMuc = new SimpleStringProperty(thongTinNoiThat.getTenHangMuc());
        this.VatLieu = new SimpleStringProperty(thongTinNoiThat.getChiTiet());
        this.ThanhTien = new SimpleLongProperty(Long.parseLong(thongTinNoiThat.getThanhTien()));
        this.KhoiLuong = new SimpleFloatProperty(Float.parseFloat(thongTinNoiThat.getSoLuong()));
    }

    public void setSTT(String STT) {
        this.STT.setValue(STT);
    }

    public void setCao(float cao) {
        this.Cao.setValue(cao);
    }

    public void setDai(float dai) {
        this.Dai.setValue(dai);
    }

    public void setRong(float rong) {
        this.Rong.setValue(rong);
    }

    public void setDonGia(long donGia) {
        this.DonGia.setValue(donGia);
    }

    public void setDonVi(String donVi) {
        this.DonVi.setValue(donVi);
    }

    public void setHangMuc(String hangMuc) {
        this.HangMuc.setValue(hangMuc);
    }

    public void setVatLieu(String vatLieu) {
        this.VatLieu.setValue(vatLieu);
    }

    public void setThanhTien(long thanhTien) {
        this.ThanhTien.setValue(thanhTien);
    }

    public void setKhoiLuong(float khoiLuong) {
        this.KhoiLuong.setValue(khoiLuong);
    }
}
