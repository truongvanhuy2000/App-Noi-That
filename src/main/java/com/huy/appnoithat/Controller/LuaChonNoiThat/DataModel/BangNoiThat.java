package com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.DataModel.Entity.NoiThatEntity;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BangNoiThat {
    private final SimpleStringProperty STT;
    private final SimpleDoubleProperty Cao;
    private final SimpleDoubleProperty Dai;
    private final SimpleDoubleProperty Rong;
    private final SimpleLongProperty DonGia;
    private final SimpleStringProperty DonVi;
    private final SimpleObjectProperty<NoiThatItem> HangMuc;
    private final SimpleObjectProperty<NoiThatItem> VatLieu;
    private final SimpleLongProperty ThanhTien;
    private final SimpleDoubleProperty KhoiLuong;
    @Setter
    private ItemType itemType;


    @Builder
    public BangNoiThat(String id, Double cao, Double dai, Double rong, Long donGia, String donVi, NoiThatItem hangMuc,
                       NoiThatItem vatLieu, Long thanhTien, Double khoiLuong, ItemType itemType) {
        STT = new SimpleStringProperty(id);
        Cao = new SimpleDoubleProperty(cao);
        Dai = new SimpleDoubleProperty(dai);
        Rong = new SimpleDoubleProperty(rong);
        DonGia = new SimpleLongProperty(donGia);
        DonVi = new SimpleStringProperty(donVi);
        HangMuc = new SimpleObjectProperty<>(hangMuc);
        VatLieu = new SimpleObjectProperty<>(vatLieu);
        ThanhTien = new SimpleLongProperty(thanhTien);
        KhoiLuong = new SimpleDoubleProperty(khoiLuong);
        this.itemType = itemType;
    }

    public BangNoiThat(ThongTinNoiThat thongTinNoiThat) {
        this.STT = new SimpleStringProperty(thongTinNoiThat.getSTT());
        this.Cao = new SimpleDoubleProperty(Double.parseDouble(thongTinNoiThat.getCao()));
        this.Dai = new SimpleDoubleProperty(Double.parseDouble(thongTinNoiThat.getDai()));
        this.Rong = new SimpleDoubleProperty(Double.parseDouble(thongTinNoiThat.getRong()));
        this.DonGia = new SimpleLongProperty(Utils.convertDecimalToLong(thongTinNoiThat.getDonGia()));
        this.DonVi = new SimpleStringProperty(thongTinNoiThat.getDonViTinh());
        this.HangMuc = new SimpleObjectProperty<>(thongTinNoiThat.getTenHangMuc());
        this.VatLieu = new SimpleObjectProperty<>(thongTinNoiThat.getChiTiet());
        this.ThanhTien = new SimpleLongProperty(Utils.convertDecimalToLong(thongTinNoiThat.getThanhTien()));
        this.KhoiLuong = new SimpleDoubleProperty(Double.parseDouble(thongTinNoiThat.getSoLuong()));
        this.itemType = thongTinNoiThat.getItemType();
    }

    public BangNoiThat(BangNoiThat bangNoiThat) {
        this.STT = new SimpleStringProperty(bangNoiThat.getSTT().getValue());
        this.Cao = new SimpleDoubleProperty(bangNoiThat.getCao().getValue());
        this.Dai = new SimpleDoubleProperty(bangNoiThat.getDai().getValue());
        this.Rong = new SimpleDoubleProperty(bangNoiThat.getRong().getValue());
        this.DonGia = new SimpleLongProperty(bangNoiThat.getDonGia().getValue());
        this.DonVi = new SimpleStringProperty(bangNoiThat.getDonVi().getValue());
        this.HangMuc = new SimpleObjectProperty<>(bangNoiThat.getHangMuc().getValue());
        this.VatLieu = new SimpleObjectProperty<>(bangNoiThat.getVatLieu().getValue());
        this.ThanhTien = new SimpleLongProperty(bangNoiThat.getThanhTien().getValue());
        this.KhoiLuong = new SimpleDoubleProperty(bangNoiThat.getKhoiLuong().getValue());
        this.itemType = bangNoiThat.getItemType();
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

    public void setHangMuc(NoiThatItem hangMuc) {
        this.HangMuc.setValue(hangMuc);
    }

    public void setVatLieu(NoiThatItem vatLieu) {
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
                .itemType(itemType)
                .build();
    }

    @Builder
    public static class Snapshot implements Memento {
        private final BangNoiThat bangNoiThat;
        private final String id;
        private final double cao;
        private final double dai;
        private final double rong;
        private final long donGia;
        private final String donVi;
        private final NoiThatItem hangMuc;
        private final NoiThatItem vatLieu;
        private final long thanhTien;
        private final double khoiLuong;
        private final ItemType itemType;

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
            bangNoiThat.setItemType(itemType);
        }
    }
}
