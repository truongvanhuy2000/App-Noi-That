package com.huy.appnoithat.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ThongSo {
    private int id;
    private Float dai;
    private Float rong;
    private Float cao;
    private String don_vi;
    private Long don_gia;
}
