package com.huy.appnoithat.DataModel;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Shared.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThongTinNoiThat {
    String STT;
    String TenHangMuc;
    String ChiTiet;
    String Dai;
    String Rong;
    String Cao;
    String DonViTinh;
    String DonGia;
    String SoLuong;
    String ThanhTien;

    public ThongTinNoiThat(BangNoiThat bangNoiThat) {
        STT = bangNoiThat.getSTT().getValue();
        TenHangMuc = bangNoiThat.getHangMuc().getValue();
        ChiTiet = bangNoiThat.getVatLieu().getValue();
        Dai = bangNoiThat.getDai().getValue().toString();
        Rong = bangNoiThat.getRong().getValue().toString();
        Cao = bangNoiThat.getCao().getValue().toString();
        DonViTinh = bangNoiThat.getDonVi().getValue();
        DonGia = Utils.convertLongToDecimal(bangNoiThat.getDonGia().getValue());
        SoLuong = bangNoiThat.getKhoiLuong().getValue().toString();
        ThanhTien = Utils.convertLongToDecimal(bangNoiThat.getThanhTien().getValue());
    }
}
