package com.huy.appnoithat.Entity;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class HangMuc {
    private int id;
    private String name;
    List<VatLieu> vatLieuList;

    public void add(VatLieu vatLieu) {
        vatLieuList.add(vatLieu);
    }
}
