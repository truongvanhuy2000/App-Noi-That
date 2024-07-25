package com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.Excel;

import com.huy.appnoithat.DataModel.*;
import com.huy.appnoithat.DataModel.SaveFile.TabData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ExportMultipleXLS extends ExportXLS {
    final static Logger LOGGER = LogManager.getLogger(ExportMultipleXLS.class);
    private List<TabData> exportData;
    public ExportMultipleXLS() {
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
        stylistFactory = new StylistFactory(workbook);
    }
    public void setUpDataForExport(List<TabData> exportData) {
        this.exportData = exportData;
    }
    public boolean export(File exportDirectory){
        LOGGER.info("Exporting to XLSX file");
        try {
            setOutputFile(exportDirectory);
            cloneSheet();
            for (int index = 0; index < exportData.size(); index++) {
                TabData tabData = exportData.get(index);
                XSSFSheet spreadsheet = workbook.getSheetAt(index + 1);
                DataPackage dataPackage = tabData.getDataPackage();
                exportSingleSheet(spreadsheet, dataPackage);
            }
            workbook.removeSheetAt(0);
            save();
        } catch (Exception e){
            return false;
        }
        return true;
    }
    private void exportSingleSheet(XSSFSheet spreadSheet, DataPackage dataPackage) throws IOException {
        ThongTinCongTy thongTinCongTy = setThongTinCongTy(dataPackage.getThongTinCongTy());
        ThongTinKhachHang thongTinKhachHang = setThongTinKhachHang(dataPackage.getThongTinKhachHang());
        List<ThongTinNoiThat> thongTinNoiThatList = setThongTinNoiThatList(dataPackage.getThongTinNoiThatList());
        ThongTinThanhToan thongTinThanhToan = dataPackage.getThongTinThanhToan();
        String noteArea = dataPackage.getNoteArea();
        exportThongTinCongTy(spreadSheet, thongTinCongTy);
        exportLogo(spreadSheet, new ByteArrayInputStream(thongTinCongTy.getLogo()));
        exportThongTinKhachHang(spreadSheet, thongTinKhachHang);
        int rowID = exportNoiThat(spreadSheet, thongTinNoiThatList);
        exportBangThanhToan(spreadSheet, ++rowID, thongTinThanhToan);
        exportNoteArea(spreadSheet, ++rowID, noteArea);
    }
    private void cloneSheet() {
        for (int index = 0; index < exportData.size(); index++) {
            workbook.cloneSheet(0);
            String sheetName = exportData.get(index).getTabName();
            if (workbook.getSheet(sheetName) != null) {
                sheetName = sheetName + " " + index;
            }
            workbook.setSheetName(index + 1, sheetName);
        }
    }
}
