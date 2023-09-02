package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.Service.FileExport.Excel.ExportXLS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ExportXLSTest {
    private ExportXLS exportXLS;
    @BeforeEach
    void setUp() {
        ThongTinCongTy thongTinCongTy = null;
        ThongTinKhachHang thongTinKhachHang = new ThongTinKhachHang(
                "Nguyễn Văn A",
                "123 Nguyễn Văn Cừ, Quận 5, TP.HC",
                "0123456789",
                "1/2/1999",
                "tu quan ao");
        try {
            thongTinCongTy = new ThongTinCongTy(
                    new FileInputStream("/home/huy/Downloads/att-logo.png"),
                    "Công ty TNHH Nội Thất Huy",
                    "123 Nguyễn Văn Cừ, Quận 5, TP.HCM",
                    "123 Nguyễn Văn Cừ, Quận 5, TP.HCM",
                    "0123456789",
                    "huy@gmail.com"

            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ThongTinNoiThat thongTinNoiThat = new ThongTinNoiThat(
                "I",
                "Noi That Tu Bep",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "1000000");
        ThongTinNoiThat thongTinNoiThat1 = new ThongTinNoiThat(
                "1",
                "Noi That Tu Bep",
                "sadsdas",
                "100",
                "200",
                "300",
                "cm",
                "500",
                "600",
                "1000000");
        List<ThongTinNoiThat> thongTinNoiThatList = new ArrayList<>();
        thongTinNoiThatList.add(thongTinNoiThat);
        thongTinNoiThatList.add(thongTinNoiThat1);
        exportXLS = new ExportXLS();
        exportXLS.setThongTinCongTy(thongTinCongTy);
        exportXLS.setThongTinKhachHang(thongTinKhachHang);
        exportXLS.setThongTinNoiThatList(thongTinNoiThatList);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void export() {
        try {
            exportXLS.export();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}