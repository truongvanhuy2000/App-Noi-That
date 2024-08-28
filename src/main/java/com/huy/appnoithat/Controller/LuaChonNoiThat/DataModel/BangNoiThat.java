package com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BangNoiThat {
    private final SimpleStringProperty STT;
    private final SimpleDoubleProperty Cao;
    private final SimpleDoubleProperty Dai;
    private final SimpleDoubleProperty Rong;
    private final SimpleLongProperty DonGia;
    private final SimpleStringProperty DonVi;
    private final SimpleStringProperty HangMuc;
    private final SimpleStringProperty VatLieu;
    private final SimpleLongProperty ThanhTien;
    private final SimpleDoubleProperty KhoiLuong;

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

    public BangNoiThat() {
        STT = new SimpleStringProperty("");
        Cao = new SimpleDoubleProperty(0);
        Dai = new SimpleDoubleProperty(0);
        Rong = new SimpleDoubleProperty(0);
        DonGia = new SimpleLongProperty(0);
        DonVi = new SimpleStringProperty("");
        HangMuc = new SimpleStringProperty("");
        VatLieu = new SimpleStringProperty("");
        ThanhTien = new SimpleLongProperty(0);
        KhoiLuong = new SimpleDoubleProperty(0);
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

    public Memento createSnapshot() {
        return Snapshot.builder()
                .bangNoiThat(this)
                .id(STT.getValue())
                .cao(Cao.getValue())
                .dai(Dai.getValue())
                .rong(Rong.getValue())
                .donGia(DonGia.getValue())
                .donVi(DonVi.getValue())
                .hangMuc(HangMuc.getValue())
                .vatLieu(VatLieu.getValue())
                .thanhTien(ThanhTien.getValue())
                .khoiLuong(KhoiLuong.getValue())
                .build();
    }

    @Builder
    public static class Snapshot implements Memento {
        private BangNoiThat bangNoiThat;
        private String id;
        private double cao;
        private double dai;
        private double rong;
        private long donGia;
        private String donVi;
        private String hangMuc;
        private String vatLieu;
        private long thanhTien;
        private double khoiLuong;

        @Override
        public void restore() {
            bangNoiThat.setSTT(id);
            bangNoiThat.setCao(cao);
            bangNoiThat.setDai(dai);
            bangNoiThat.setRong(rong);
            bangNoiThat.setDonGia(donGia);
            bangNoiThat.setDonVi(donVi);
            bangNoiThat.setHangMuc(hangMuc);
            bangNoiThat.setVatLieu(vatLieu);
            bangNoiThat.setThanhTien(thanhTien);
            bangNoiThat.setKhoiLuong(khoiLuong);
        }
    }
}
