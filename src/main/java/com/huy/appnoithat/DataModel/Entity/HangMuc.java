package com.huy.appnoithat.DataModel.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HangMuc implements NoiThatEntity {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("vatLieu")
    List<VatLieu> vatLieuList;

    public void add(VatLieu vatLieu) {
        vatLieuList.add(vatLieu);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
