package com.huy.appnoithat.DataModel;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Shared.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ThongTinThanhToan {
    private String DatCocThietKe10;
    private String DatCocThiCong30;
    private String HangDenChanCongTrinh50;
    private String NghiemThuQuyet;
    public ThongTinThanhToan(BangThanhToan bangThanhToan) {
        DatCocThietKe10 = Utils.convertLongToDecimal(bangThanhToan.getDatCocThietKe10().getValue());
        DatCocThiCong30 = Utils.convertLongToDecimal(bangThanhToan.getDatCocThiCong30().getValue());
        HangDenChanCongTrinh50 = Utils.convertLongToDecimal(bangThanhToan.getHangDenChanCongTrinh50().getValue());
        NghiemThuQuyet = Utils.convertLongToDecimal(bangThanhToan.getNghiemThuQuyet().getValue());
    }
}
