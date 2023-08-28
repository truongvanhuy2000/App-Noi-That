package com.huy.appnoithat.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
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
}
