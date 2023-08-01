package com.huy.appnoithat.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class NoiThat {
    private int id;
    private String name;
    List<HangMuc> hangMucList;

    public void add(HangMuc hangMuc) {
        hangMucList.add(hangMuc);
    }
}
