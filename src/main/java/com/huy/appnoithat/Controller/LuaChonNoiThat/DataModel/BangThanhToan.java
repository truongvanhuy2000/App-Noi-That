package com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.units.qual.A;

@Getter
public class BangThanhToan {
    private final SimpleLongProperty DatCocThietKe10;
    private final SimpleLongProperty DatCocThiCong30;
    private final SimpleLongProperty HangDenChanCongTrinh50;
    private final SimpleLongProperty NghiemThuQuyet;
    private final SimpleLongProperty TongTien;

    public BangThanhToan(Long datCocThietKe10, Long datCocThiCong30, Long hangDenChanCongTrinh50, Long nghiemThuQuyet, Long tongTien) {
        DatCocThietKe10 = new SimpleLongProperty(datCocThietKe10);
        DatCocThiCong30 = new SimpleLongProperty(datCocThiCong30);
        HangDenChanCongTrinh50 = new SimpleLongProperty(hangDenChanCongTrinh50);
        NghiemThuQuyet = new SimpleLongProperty(nghiemThuQuyet);
        TongTien = new SimpleLongProperty(tongTien);
    }

    public BangThanhToan(ThongTinThanhToan thongTinThanhToan) {
        DatCocThietKe10 = new SimpleLongProperty(Utils.convertDecimalToLong(thongTinThanhToan.getDatCocThietKe10()));
        DatCocThiCong30 = new SimpleLongProperty(Utils.convertDecimalToLong(thongTinThanhToan.getDatCocThiCong30()));
        HangDenChanCongTrinh50 = new SimpleLongProperty(Utils.convertDecimalToLong(thongTinThanhToan.getHangDenChanCongTrinh50()));
        NghiemThuQuyet = new SimpleLongProperty(Utils.convertDecimalToLong(thongTinThanhToan.getNghiemThuQuyet()));
        TongTien = new SimpleLongProperty(Utils.convertDecimalToLong(thongTinThanhToan.getTongTien()));
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

    public void setTongTien(long tongTien) {
        this.TongTien.set(tongTien);
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

    public static BangThanhToan empty() {
        return new BangThanhToan(0L, 0L, 0L, 0L, 0L);
    }

    @Getter
    public static class Percentage {
        private final SimpleIntegerProperty DatCocThietKePercentage;
        private final SimpleIntegerProperty DatCocThiCongPercentage;
        private final SimpleIntegerProperty HangDenChanCongTrinhPercentage;

        @Builder
        public Percentage(Integer datCocThietKePercentage, Integer datCocThiCongPercentage, Integer hangDenChanCongTrinhPercentage) {
            DatCocThietKePercentage = new SimpleIntegerProperty(datCocThietKePercentage);
            DatCocThiCongPercentage = new SimpleIntegerProperty(datCocThiCongPercentage);
            HangDenChanCongTrinhPercentage = new SimpleIntegerProperty(hangDenChanCongTrinhPercentage);
        }

        public Memento createSnapshot() {
            return Snapshot.builder(this)
                    .DatCocThietKePercentage(DatCocThietKePercentage.get())
                    .DatCocThiCongPercentage(DatCocThiCongPercentage.get())
                    .HangDenChanCongTrinhPercentage(HangDenChanCongTrinhPercentage.get())
                    .build();
        }

        public static Percentage createDefault() {
            return Percentage.builder()
                    .datCocThietKePercentage(10)
                    .datCocThiCongPercentage(30)
                    .hangDenChanCongTrinhPercentage(50)
                    .build();
        }

        public void addListener(ChangeListener<Number> changeListener) {
            getDatCocThiCongPercentage().addListener(changeListener);
            getDatCocThietKePercentage().addListener(changeListener);
            getHangDenChanCongTrinhPercentage().addListener(changeListener);
        }

        @Builder(builderMethodName = "hiddenBuilder")
        @RequiredArgsConstructor
        public static class Snapshot implements Memento {
            private final Percentage percentage;
            private final int DatCocThietKePercentage;
            private final int DatCocThiCongPercentage;
            private final int HangDenChanCongTrinhPercentage;

            public static SnapshotBuilder builder(Percentage percentage) {
                return hiddenBuilder().percentage(percentage);
            }

            @Override
            public void restore() {
                percentage.getDatCocThietKePercentage().set(DatCocThietKePercentage);
                percentage.getDatCocThiCongPercentage().set(DatCocThiCongPercentage);
                percentage.getHangDenChanCongTrinhPercentage().set(HangDenChanCongTrinhPercentage);
            }
        }
    }
}
