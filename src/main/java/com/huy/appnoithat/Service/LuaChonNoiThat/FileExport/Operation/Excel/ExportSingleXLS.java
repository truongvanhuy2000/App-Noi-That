package com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.Excel;

import com.huy.appnoithat.DataModel.*;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.ExportFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ExportSingleXLS extends ExportXLS implements ExportFile {
    final static Logger LOGGER = LogManager.getLogger(ExportSingleXLS.class);
    // USED FOR TESTING ONLY
    private ThongTinCongTy thongTinCongTy;
    private ThongTinKhachHang thongTinKhachHang;
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
        exportLogo(spreadsheet, new ByteArrayInputStream(thongTinCongTy.getLogo()));
        exportThongTinKhachHang(spreadsheet, this.thongTinKhachHang);
        int rowID = exportNoiThat(spreadsheet, this.thongTinNoiThatList);
        exportBangThanhToan(spreadsheet, ++rowID, this.thongTinThanhToan);
        exportNoteArea(spreadsheet, ++rowID, this.noteArea);
        save();
    }

    @Override
    public void setUpDataForExport(DataPackage dataForExport) {
        this.thongTinCongTy = setThongTinCongTy(dataForExport.getThongTinCongTy());
        this.thongTinKhachHang = setThongTinKhachHang(dataForExport.getThongTinKhachHang());
        this.thongTinNoiThatList = setThongTinNoiThatList(dataForExport.getThongTinNoiThatList());
        this.thongTinThanhToan = dataForExport.getThongTinThanhToan();
        this.noteArea = dataForExport.getNoteArea();
    }
}
