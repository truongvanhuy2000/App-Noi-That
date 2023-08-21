package com.huy.appnoithat.Entity;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class NoiThat {
    private int id;
    private String name;
    List<HangMuc> hangMucList;
    public void add(HangMuc hangMuc) {
        hangMucList.add(hangMuc);
    }
    @Override
    public String toString() {
        return this.name;
    }
}
