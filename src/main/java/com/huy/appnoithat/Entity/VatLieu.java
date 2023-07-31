package com.huy.appnoithat.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VatLieu {
    private int id;
    private String name;
    ThongSo thongSo;
}
