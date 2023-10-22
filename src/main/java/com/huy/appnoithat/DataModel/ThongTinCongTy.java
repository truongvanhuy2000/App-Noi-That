package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Data
@NoArgsConstructor
public class ThongTinCongTy {
    @JsonProperty("logo")
    InputStream logo;
    @JsonProperty("tenCongTy")
    String tenCongTy;
    @JsonProperty("diaChiVanPhong")
    String diaChiVanPhong;
    @JsonProperty("diaChiXuong")
    String diaChiXuong;
    @JsonProperty("soDienThoai")
    String soDienThoai;
    @JsonProperty("email")
    String email;

    public ThongTinCongTy(InputStream logo, String tenCongTy, String diaChiVanPhong, String diaChiXuong, String soDienThoai, String email) {
        this.logo = logo;
        this.tenCongTy = tenCongTy;
        this.diaChiVanPhong = diaChiVanPhong;
        this.diaChiXuong = diaChiXuong;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }
    @JsonGetter("logo")
    public String getBase64Logo() throws IOException {
        byte[] imageData = logo.readAllBytes();
        return Base64.getEncoder().encodeToString(imageData);
    }
    @JsonSetter("logo")
    public void setBase64Logo(String base64Logo) {
        byte[] imageData = Base64.getDecoder().decode(base64Logo);
        this.logo = new ByteArrayInputStream(imageData);
    }
}
