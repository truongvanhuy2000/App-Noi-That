package com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel;

import javafx.beans.property.SimpleLongProperty;
import lombok.Getter;

@Getter
public class BangThanhToan {
    private SimpleLongProperty DatCocThietKe10;
    private SimpleLongProperty DatCocThiCong30;
    private SimpleLongProperty HangDenChanCongTrinh50;
    private SimpleLongProperty NghiemThuQuyet;

    public BangThanhToan(Long datCocThietKe10, Long datCocThiCong30, Long hangDenChanCongTrinh50, Long nghiemThuQuyet) {
        DatCocThietKe10 = new SimpleLongProperty(datCocThietKe10);
        DatCocThiCong30 = new SimpleLongProperty(datCocThiCong30);
        HangDenChanCongTrinh50 = new SimpleLongProperty(hangDenChanCongTrinh50);
        NghiemThuQuyet = new SimpleLongProperty(nghiemThuQuyet);
    }

    public void setDatCocThietKe10(long datCocThietKe10) {
        this.DatCocThietKe10.set(datCocThietKe10);
    }

    public void setDatCocThiCong30(long datCocThiCong30) {
        this.DatCocThiCong30.set(datCocThiCong30);
    }

    public void setHangDenChanCongTrinh50(long hangDenChanCongTrinh50) {
        this.HangDenChanCongTrinh50.set(hangDenChanCongTrinh50);
    }

    public void setNghiemThuQuyet(long nghiemThuQuyet) {
        this.NghiemThuQuyet.set(nghiemThuQuyet);
    }

    @Override
    public String toString() {
        return "BangThanhToan{" +
                "DatCocThietKe10=" + DatCocThietKe10 +
                ", DatCocThiCong30=" + DatCocThiCong30 +
                ", HangDenChanCongTrinh50=" + HangDenChanCongTrinh50 +
                ", NghiemThuQuyet=" + NghiemThuQuyet +
                '}';
    }
}
