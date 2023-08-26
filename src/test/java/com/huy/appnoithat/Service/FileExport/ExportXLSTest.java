package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.DataModel.Employee;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

class ExportXLSTest {
    private ExportXLS exportXLS;
    @BeforeEach
    void setUp() {
        ThongTinCongTy thongTinCongTy = null;
        ThongTinKhachHang thongTinKhachHang = new ThongTinKhachHang(
                "Khach Hang: Nguyễn Văn A",
                "Dia Chi: 123 Nguyễn Văn Cừ, Quận 5, TP.HC",
                "Dien Thoai: 0123456789",
                "Ngay: 1/2/1999",
                "San Pham: tu quan ao");
        try {
            thongTinCongTy = new ThongTinCongTy(
                    new FileInputStream("/home/huy/Downloads/att-logo.png"),
                    "Công ty TNHH Nội Thất Huy",
                    "Địa chỉ Cty: 123 Nguyễn Văn Cừ, Quận 5, TP.HCM",
                    "Địa chỉ xuong: 123 Nguyễn Văn Cừ, Quận 5, TP.HCM",
                    "SĐT: 0123456789",
                    "Email: huy@gmail.com"

            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        exportXLS = new ExportXLS();
        exportXLS.setThongTinCongTy(thongTinCongTy);
        exportXLS.setThongTinKhachHang(thongTinKhachHang);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void export() {
        try {
            exportXLS.export();
            exportXLS.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}