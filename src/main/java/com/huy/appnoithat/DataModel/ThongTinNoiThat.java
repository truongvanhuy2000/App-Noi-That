package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.appnoithat.common.Utils;
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

    /**
     * @param bangNoiThat This constructor will convert a BangNoiThat object to ThongTinNoiThat object
     *                    This is used for exporting data to excel file and printing data to pdf file later on
     */
    public ThongTinNoiThat(BangNoiThat bangNoiThat) {
        STT = bangNoiThat.getSTT().getValue();
        TenHangMuc = bangNoiThat.getHangMuc().getValue();
        ChiTiet = bangNoiThat.getVatLieu().getValue();
        // Convert this to long because the product owner want so
        Dai = Long.valueOf(bangNoiThat.getDai().getValue().longValue()).toString();
        Rong = Long.valueOf(bangNoiThat.getRong().getValue().longValue()).toString();
        Cao = Long.valueOf(bangNoiThat.getCao().getValue().longValue()).toString();
        DonViTinh = bangNoiThat.getDonVi().getValue();
        DonGia = Utils.convertLongToDecimal(bangNoiThat.getDonGia().getValue());
        SoLuong = bangNoiThat.getKhoiLuong().getValue().toString();
        ThanhTien = Utils.convertLongToDecimal(bangNoiThat.getThanhTien().getValue());
    }
}
