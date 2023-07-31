package com.huy.appnoithat.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class NoiThat {
    private int id;
    private String name;
    List<HangMuc> hangMucList;
}
