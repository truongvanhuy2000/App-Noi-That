package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LapBaoGiaInfo {
    @JsonProperty("tenCongTy")
    private String tenCongTy;
    @JsonProperty("diaChiVanPhong")
    private String diaChiVanPhong;
    @JsonProperty("soDienThoai")
    private String soDienThoai;
    @JsonProperty("diaChiXuong")
    private String diaChiXuong;
    @JsonProperty("email")
    private String email;
    @JsonProperty("logo")
    private InputStream logo;
    @JsonProperty("note")
    private String note;
}
