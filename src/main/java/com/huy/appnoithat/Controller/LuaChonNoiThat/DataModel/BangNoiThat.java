package com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BangNoiThat {
    private SimpleStringProperty STT;
    private SimpleDoubleProperty Cao;
    private SimpleDoubleProperty Dai;
    private SimpleDoubleProperty Rong;
    private SimpleLongProperty DonGia;
    private SimpleStringProperty DonVi;
    private SimpleStringProperty HangMuc;
    private SimpleStringProperty VatLieu;
    private SimpleLongProperty ThanhTien;
    private SimpleDoubleProperty KhoiLuong;

    public BangNoiThat(String id, Double cao, Double dai, Double rong, Long donGia, String donVi, String hangMuc, String vatLieu, Long thanhTien, Double khoiLuong) {
        STT = new SimpleStringProperty(id);
        Cao = new SimpleDoubleProperty(cao);
        Dai = new SimpleDoubleProperty(dai);
        Rong = new SimpleDoubleProperty(rong);
        DonGia = new SimpleLongProperty(donGia);
        DonVi = new SimpleStringProperty(donVi);
        HangMuc = new SimpleStringProperty(hangMuc);
        VatLieu = new SimpleStringProperty(vatLieu);
        ThanhTien = new SimpleLongProperty(thanhTien);
        KhoiLuong = new SimpleDoubleProperty(khoiLuong);
    }

    public BangNoiThat(ThongTinNoiThat thongTinNoiThat) {
        this.STT = new SimpleStringProperty(thongTinNoiThat.getSTT());
        this.Cao = new SimpleDoubleProperty(Double.parseDouble(thongTinNoiThat.getCao()));
        this.Dai = new SimpleDoubleProperty(Double.parseDouble(thongTinNoiThat.getDai()));
        this.Rong = new SimpleDoubleProperty(Double.parseDouble(thongTinNoiThat.getRong()));
        this.DonGia = new SimpleLongProperty(Utils.convertDecimalToLong(thongTinNoiThat.getDonGia()));
        this.DonVi = new SimpleStringProperty(thongTinNoiThat.getDonViTinh());
        this.HangMuc = new SimpleStringProperty(thongTinNoiThat.getTenHangMuc());
        this.VatLieu = new SimpleStringProperty(thongTinNoiThat.getChiTiet());
        this.ThanhTien = new SimpleLongProperty(Utils.convertDecimalToLong(thongTinNoiThat.getThanhTien()));
        this.KhoiLuong = new SimpleDoubleProperty(Double.parseDouble(thongTinNoiThat.getSoLuong()));
    }

    public BangNoiThat(BangNoiThat bangNoiThat) {
        this.STT = new SimpleStringProperty(bangNoiThat.getSTT().getValue());
        this.Cao = new SimpleDoubleProperty(bangNoiThat.getCao().getValue());
        this.Dai = new SimpleDoubleProperty(bangNoiThat.getDai().getValue());
        this.Rong = new SimpleDoubleProperty(bangNoiThat.getRong().getValue());
        this.DonGia = new SimpleLongProperty(bangNoiThat.getDonGia().getValue());
        this.DonVi = new SimpleStringProperty(bangNoiThat.getDonVi().getValue());
        this.HangMuc = new SimpleStringProperty(bangNoiThat.getHangMuc().getValue());
        this.VatLieu = new SimpleStringProperty(bangNoiThat.getVatLieu().getValue());
        this.ThanhTien = new SimpleLongProperty(bangNoiThat.getThanhTien().getValue());
        this.KhoiLuong = new SimpleDoubleProperty(bangNoiThat.getKhoiLuong().getValue());
    }

    public void setSTT(String STT) {
        this.STT.setValue(STT);
    }

    public void setCao(double cao) {
        this.Cao.setValue(cao);
    }

    public void setDai(double dai) {
        this.Dai.setValue(dai);
    }

    public void setRong(double rong) {
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

    public void setKhoiLuong(double khoiLuong) {
        this.KhoiLuong.setValue(khoiLuong);
    }
}
