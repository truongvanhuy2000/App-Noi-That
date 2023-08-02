package com.huy.appnoithat.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserSelection {
    PhongCachNoiThat phongCachNoiThat;
    NoiThat noiThat;
    HangMuc hangMuc;
    VatLieu vatLieu;
    ThongSo thongSo;
}
