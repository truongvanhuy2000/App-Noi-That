package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.DataModel.Employee;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
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
//        try {
//            thongTinCongTy = new ThongTinCongTy(
//                    new FileInputStream("/home/huy/Pictures/download.jpeg"),
//                    "Công ty TNHH Nội Thất Huy",
//                    "Địa chỉ Cty: 123 Nguyễn Văn Cừ, Quận 5, TP.HCM",
//                    "Địa chỉ xuong: 123 Nguyễn Văn Cừ, Quận 5, TP.HCM",
//                    "SĐT: 0123456789",
//                    "Email: huy@gmail.com"
//
//            );
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        exportXLS = new ExportXLS();
//        exportXLS.setThongTinCongTy(thongTinCongTy);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void exportImage() {
        try {
            exportXLS.exportLogo(new FileInputStream("/home/huy/Downloads/att-logo.png"));
            exportXLS.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}