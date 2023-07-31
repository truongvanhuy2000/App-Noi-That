package com.huy.appnoithat.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ThongSo {
    private int id;
    private int dai;
    private int rong;
    private int cao;
    private String don_vi;
    private int don_gia;
}
