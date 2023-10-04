package com.huy.appnoithat.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class ThongSo {
    @JsonProperty("id")
    private int id;
    @JsonProperty("dai")
    private Double dai;
    @JsonProperty("rong")
    private Double rong;
    @JsonProperty("cao")
    private Double cao;
    @JsonProperty("donVi")
    private String don_vi;
    @JsonProperty("donGia")
    private Long don_gia;

    public ThongSo(int id, Double dai, Double rong, Double cao, String don_vi, Long don_gia) {
        this.id = id;
        this.dai = Objects.requireNonNullElse(dai, 0.0);
        this.rong = Objects.requireNonNullElse(rong, 0.0);
        this.cao = Objects.requireNonNullElse(cao, 0.0);
        this.don_vi = don_vi;
        this.don_gia = don_gia;
    }
}
