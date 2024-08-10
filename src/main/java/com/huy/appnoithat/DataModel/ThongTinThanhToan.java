package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.appnoithat.common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinThanhToan {
    @JsonProperty("DatCocThietKe10")
    private String DatCocThietKe10;
    @JsonProperty("DatCocThiCong30")
    private String DatCocThiCong30;
    @JsonProperty("HangDenChanCongTrinh50")
    private String HangDenChanCongTrinh50;
    @JsonProperty("NghiemThuQuyet")
    private String NghiemThuQuyet;
    @JsonProperty("TongTien")
    private String TongTien;

    public ThongTinThanhToan(BangThanhToan bangThanhToan) {
        DatCocThietKe10 = Utils.convertLongToDecimal(bangThanhToan.getDatCocThietKe10().getValue());
        DatCocThiCong30 = Utils.convertLongToDecimal(bangThanhToan.getDatCocThiCong30().getValue());
        HangDenChanCongTrinh50 = Utils.convertLongToDecimal(bangThanhToan.getHangDenChanCongTrinh50().getValue());
        NghiemThuQuyet = Utils.convertLongToDecimal(bangThanhToan.getNghiemThuQuyet().getValue());
        TongTien = Utils.convertLongToDecimal(bangThanhToan.getTongTien().getValue());
    }
}
