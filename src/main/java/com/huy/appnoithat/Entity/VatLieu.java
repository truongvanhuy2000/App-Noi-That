package com.huy.appnoithat.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatLieu {
    private int id;
    private String name;
    ThongSo thongSo;
    @Override
    public String toString() {
        return this.name;
    }
}
