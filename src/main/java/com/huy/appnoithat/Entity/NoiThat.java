package com.huy.appnoithat.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.appnoithat.Entity.Common.CommonItemInterface;
import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class NoiThat implements CommonItemInterface {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("hangMuc")
    List<HangMuc> hangMucList;
    public void add(HangMuc hangMuc) {
        hangMucList.add(hangMuc);
    }
    @Override
    public String toString() {
        return this.name;
    }
}
