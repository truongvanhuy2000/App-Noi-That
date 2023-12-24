package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongTinCongTy {
    @JsonProperty("logo")
    byte[] logo;
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
    @JsonProperty("createdDate")
    Date createdDate;

    public ThongTinCongTy(byte[] logo, String tenCongTy, String diaChiVanPhong, String diaChiXuong, String soDienThoai, String email) {
        this.logo = logo;
        this.tenCongTy = tenCongTy;
        this.diaChiVanPhong = diaChiVanPhong;
        this.diaChiXuong = diaChiXuong;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }
    @JsonGetter("logo")
    public String getBase64Logo() {
        if (logo == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(logo);
    }
    @JsonSetter("logo")
    public void setBase64Logo(String base64Logo) {
        if (StringUtils.isBlank(base64Logo)) {
            return;
        }
        this.logo = Base64.getDecoder().decode(base64Logo);
    }
}
