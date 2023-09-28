package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinNoiThat {
    @JsonProperty("STT")
    String STT;
    @JsonProperty("TenHangMuc")
    String TenHangMuc;
    @JsonProperty("ChiTiet")
    String ChiTiet;
    @JsonProperty("Dai")
    String Dai;
    @JsonProperty("Rong")
    String Rong;
    @JsonProperty("Cao")
    String Cao;
    @JsonProperty("DonViTinh")
    String DonViTinh;
    @JsonProperty("DonGia")
    String DonGia;
    @JsonProperty("SoLuong")
    String SoLuong;
    @JsonProperty("ThanhTien")
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
