package com.huy.appnoithat.DataModel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class ThongTinCongTy {
    InputStream logo;
    String tenCongTy;
    String diaChiVanPhong;
    String diaChiXuong;
    String soDienThoai;
    String email;
}
