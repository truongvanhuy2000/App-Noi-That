package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import com.huy.appnoithat.Service.FileExport.Excel.ExportXLS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                "- Thùng: nhựa Picomat 17mm chống nước tuyệt đối.\n" +
                        "- Cánh: MDF chống ẩm 17mm phủ Melamine An Cường.\n" +
                        "- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối",
                "100",
                "200",
                "300",
                "cm",
                "500",
                "600",
                "1000000");
        ThongTinThanhToan thongTinThanhToan = new ThongTinThanhToan(
                "1000000",
                "2000000",
                "3000000",
                "4000000");
        List<ThongTinNoiThat> thongTinNoiThatList = new ArrayList<>();
        thongTinNoiThatList.add(thongTinNoiThat);
        thongTinNoiThatList.add(thongTinNoiThat1);
        exportXLS = new ExportXLS();
        exportXLS.setThongTinCongTy(thongTinCongTy);
        exportXLS.setThongTinKhachHang(thongTinKhachHang);
        exportXLS.setThongTinNoiThatList(thongTinNoiThatList);
        exportXLS.setThongTinThanhToan(thongTinThanhToan);
    }

    @Test
    void testExtractText() {
        String inputText = "- Thùng: nhựa Picomat 17mm chống nước tuyệt đối.\n" +
                "- Cánh: MDF chống ẩm 17mm phủ Melamine An Cường.\n" +
                "- Hậu: Nhôm Alu dày 3mm chống nước tuyệt đối";

        // Define a regular expression pattern to match the desired text
        Pattern pattern = Pattern.compile("-\\s(.*?):");

        // Create a Matcher object to find matches in the input text
        Matcher matcher = pattern.matcher(inputText);

        // Iterate through the matches and print the extracted text
        while (matcher.find()) {
            // Start position of the matched text
            int start = matcher.start(1);

            // End position of the matched text
            int end = matcher.end(1);

            // Extracted text between '-' and ':'
            String extractedText = inputText.substring(start, end);

            System.out.println("Extracted Text: " + extractedText);
            System.out.println("Start Position: " + start);
            System.out.println("End Position: " + end);
        }
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