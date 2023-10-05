package com.huy.appnoithat.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.appnoithat.Entity.Common.CommonItemInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatLieu implements CommonItemInterface {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("thongSo")
    ThongSo thongSo;

    @Override
    public String toString() {
        return this.name;
    }
}
