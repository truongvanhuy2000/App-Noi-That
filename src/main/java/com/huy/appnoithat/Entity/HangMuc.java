package com.huy.appnoithat.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class HangMuc {
    private int id;
    private String name;
    List<VatLieu> vatLieuList;
}
