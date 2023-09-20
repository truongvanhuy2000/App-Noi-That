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
    private Float dai;
    @JsonProperty("rong")
    private Float rong;
    @JsonProperty("cao")
    private Float cao;
    @JsonProperty("donVi")
    private String don_vi;
    @JsonProperty("donGia")
    private Long don_gia;

    public ThongSo(int id, Float dai, Float rong, Float cao, String don_vi, Long don_gia) {
        this.id = id;
        this.dai = Objects.requireNonNullElse(dai, 0f);

        this.rong = Objects.requireNonNullElse(rong, 0f);
        ;
        this.cao = Objects.requireNonNullElse(cao, 0f);
        ;
        ;
        this.don_vi = don_vi;
        this.don_gia = don_gia;
    }
}
