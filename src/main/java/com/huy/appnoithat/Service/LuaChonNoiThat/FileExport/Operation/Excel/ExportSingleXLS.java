package com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.Excel;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.ItemTypeUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.ExportFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExportSingleXLS extends ExportXLS implements ExportFile {
    final static Logger LOGGER = LogManager.getLogger(ExportSingleXLS.class);
    // USED FOR TESTING ONLY
    private final ThongTinCongTy thongTinCongTy;
    private final ThongTinKhachHang thongTinKhachHang;
    private String noteArea;
    private List<ThongTinNoiThat> thongTinNoiThatList;
    private ThongTinThanhToan thongTinThanhToan;
    private XSSFSheet spreadsheet;

    public ExportSingleXLS() {
        try {
            this.inputTemplate = new FileInputStream(DEFAULT_TEMPLATE_PATH);
            initWorkbook();
        } catch (IOException e) {
            LOGGER.error("Error while init ExportXLS");
            throw new RuntimeException(e);
        }
        this.thongTinCongTy = new ThongTinCongTy();
        this.thongTinKhachHang = new ThongTinKhachHang();
    }

    public void setThongTinNoiThatList(List<ThongTinNoiThat> thongTinNoiThatList) {
        this.thongTinNoiThatList = new ArrayList<>();
        thongTinNoiThatList.forEach(item -> {
            String stt = item.getSTT();
            if (ItemTypeUtils.determineItemType(stt) == ItemType.NUMERIC
                    || ItemTypeUtils.determineItemType(stt) == ItemType.ROMAN) {
                this.thongTinNoiThatList.add(item);
            }
        });
        rearrangeList(this.thongTinNoiThatList);
    }
    private void initWorkbook() throws IOException {
        workbook = new XSSFWorkbook(inputTemplate);
        spreadsheet = workbook.getSheet("Sheet1");
        stylistFactory = new StylistFactory(workbook);
    }

    @Override
    public void export(File exportDirectory) throws IOException {
        setOutputFile(exportDirectory);
        LOGGER.info("Exporting to XLSX file");
        exportThongTinCongTy(spreadsheet, this.thongTinCongTy);
        exportLogo(spreadsheet, this.thongTinCongTy.getLogo());
        exportThongTinKhachHang(spreadsheet, this.thongTinKhachHang);
        int rowID = exportNoiThat(spreadsheet, this.thongTinNoiThatList);
        exportBangThanhToan(spreadsheet, ++rowID, this.thongTinThanhToan);
        exportNoteArea(spreadsheet, ++rowID, this.noteArea);
        save();
    }

    @Override
    public void setUpDataForExport(DataPackage dataForExport) {
        setThongTinCongTy(dataForExport.getThongTinCongTy());
        setThongTinKhachHang(dataForExport.getThongTinKhachHang());
        setThongTinNoiThatList(dataForExport.getThongTinNoiThatList());
        this.thongTinThanhToan = dataForExport.getThongTinThanhToan();
        this.noteArea = dataForExport.getNoteArea();
    }
    private void rearrangeList(List<ThongTinNoiThat> list) {
        int romanCount = 0;
        for (ThongTinNoiThat item : list) {
            if (Objects.requireNonNull(ItemTypeUtils.determineItemType(item.getSTT())) == ItemType.ROMAN) {
                romanCount++;
                item.setSTT(ItemTypeUtils.createFullId(ItemType.ROMAN, Utils.toRoman(romanCount)));
            }
        }
    }
    private void setThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        this.thongTinCongTy.setLogo(thongTinCongTy.getLogo());
        this.thongTinCongTy.setTenCongTy(thongTinCongTy.getTenCongTy());
        this.thongTinCongTy.setDiaChiVanPhong("Địa chỉ văn phòng: " + thongTinCongTy.getDiaChiVanPhong());
        this.thongTinCongTy.setDiaChiXuong("Địa chỉ nhà xưởng: " + thongTinCongTy.getDiaChiXuong());
        this.thongTinCongTy.setSoDienThoai("Hotline: " + thongTinCongTy.getSoDienThoai());
        this.thongTinCongTy.setEmail("Email: " + thongTinCongTy.getEmail());
    }
    private void setThongTinKhachHang(ThongTinKhachHang thongTinKhachHang) {
        this.thongTinKhachHang.setTenKhachHang("Khách hàng : " + thongTinKhachHang.getTenKhachHang());
        this.thongTinKhachHang.setDiaChi("Địa chỉ: " + thongTinKhachHang.getDiaChi());
        this.thongTinKhachHang.setSoDienThoai("Điện thoại: " + thongTinKhachHang.getSoDienThoai());
        this.thongTinKhachHang.setDate("Ngày: " + thongTinKhachHang.getDate());
        this.thongTinKhachHang.setSanPham("Sản phẩm: " + thongTinKhachHang.getSanPham());
    }


}
