package com.huy.appnoithat.Service.FileExport.Excel;

import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.Service.FileExport.ExportFileService;
import com.huy.appnoithat.Shared.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class ExportXLS implements ExportFileService {
    final static Logger LOGGER = LogManager.getLogger(ExportFileService.class);
    // USED FOR TESTING ONLY
    private static final String DEFAULT_TEMPLATE_PATH = "/template.xlsx";
    private static final String DEFAULT_OUTPUT_PATH = "target/object_collection_output.xlsx";

    private ThongTinCongTy thongTinCongTy;
    private ThongTinKhachHang thongTinKhachHang;
    private List<ThongTinNoiThat> thongTinNoiThatList;

    private InputStream inputTemplate;
    private OutputStream outputFile;
    private XSSFWorkbook workbook;
    private XSSFSheet spreadsheet;
    StylistFactory stylistFactory;
    public ExportXLS(OutputStream outputFile) {
        try {
            this.inputTemplate = new FileInputStream(Objects.requireNonNull(ExportXLS.class.getResource(DEFAULT_TEMPLATE_PATH)).getFile());
            this.outputFile = outputFile;
            initWorkbook();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ExportXLS() {
        try {
            this.inputTemplate = new FileInputStream(Objects.requireNonNull(ExportXLS.class.getResource(DEFAULT_TEMPLATE_PATH)).getFile());
            this.outputFile = new FileOutputStream(DEFAULT_OUTPUT_PATH);
            initWorkbook();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initWorkbook() throws IOException {
        workbook = new XSSFWorkbook(inputTemplate);
        spreadsheet = workbook.getSheet("Sheet1");
        stylistFactory = new StylistFactory(workbook);
    }

    @Override
    public void export() throws IOException {
        LOGGER.info("Exporting to XLSX file");
        exportThongTinCongTy(this.thongTinCongTy);
        exportLogo(this.thongTinCongTy.getLogo());
        exportThongTinKhachHang(this.thongTinKhachHang);
        exportNoiThat(this.thongTinNoiThatList);
        save();
    }
    private void mergeCell(int row, int col, int rowSpan, int colSpan) {
        spreadsheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(row, row + rowSpan, col, col + colSpan));
    }
    private void customWriteForInformation(String data, int row, int cell, int fontSize, CellStyle cellStyle) {
        Cell cell0 = spreadsheet.getRow(row).getCell(cell);
        cell0.setCellValue(stylistFactory.textStyleFactory(Stylist.Style.Text_CUSTOMBOLD1, data, fontSize));
        cell0.setCellStyle(cellStyle);
    }
    private void exportThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        int mergeColumnRange = 7;
        int mergeColumnId = 2;
        int mergeRowId = 0;
        int mergeRowRange = 0;

        int cellId = 2;
        int rowId = 0;

        CellStyle cellStyle = stylistFactory.cellStyleFactory(Map.of(
                Stylist.Element.ALIGNMENT, Stylist.Style.VerticalAlignment_CENTER,
                Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
        ));

        mergeCell(mergeRowId++, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinCongTy.getTenCongTy(), rowId++, cellId, 18, cellStyle);

        mergeCell(mergeRowId++, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinCongTy.getDiaChiVanPhong(), rowId++, cellId, 13, cellStyle);

        mergeCell(mergeRowId++, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinCongTy.getDiaChiXuong(), rowId++, cellId, 13, cellStyle);

        mergeCell(mergeRowId++, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinCongTy.getSoDienThoai(), rowId++, cellId, 13, cellStyle);

        mergeCell(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinCongTy.getEmail(), rowId, cellId, 13, cellStyle);
    }

    private void exportThongTinKhachHang(ThongTinKhachHang thongTinKhachHang) {
        int mergeColumnRange = 9;
        int mergeColumnId = 0;
        int mergeRowId = 6;
        int mergeRowRange = 0;

        int cellId = 0;
        int rowId = 6;

        CellStyle cellStyle = stylistFactory.cellStyleFactory(Map.of(
                Stylist.Element.ALIGNMENT, Stylist.Style.VerticalAlignment_CENTER
        ));

        mergeCell(mergeRowId++, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinKhachHang.getTenKhachHang(), rowId++, cellId, 13, cellStyle);

        mergeCell(mergeRowId++, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinKhachHang.getSoDienThoai(), rowId++, cellId, 13, cellStyle);

        mergeCell(mergeRowId++, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinKhachHang.getDiaChi(), rowId++, cellId, 13, cellStyle);

        mergeCell(mergeRowId++, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinKhachHang.getDate(), rowId++, cellId, 13, cellStyle);

        mergeCell(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange);
        customWriteForInformation(thongTinKhachHang.getSanPham(), rowId, cellId, 13, cellStyle);
    }

    private void exportNoiThat(List<ThongTinNoiThat> thongTinNoiThats) {
        int mergeColumnRange = 7;
        int mergeColumnId = 1;
        int mergeRowId = 13;
        int mergeRowRange = 0;

        int cellId = 0;
        int rowId = 13;

        for (ThongTinNoiThat thongTinNoiThat : thongTinNoiThats) {
            spreadsheet.shiftRows(rowId, spreadsheet.getLastRowNum(), 1, true, true);
            Row newRow = createPopulatedRow(rowId, 10);
            // If STT is roman, that mean it's the merge Row, we can call it title row
            if (Utils.RomanNumber.isRoman(thongTinNoiThat.getSTT())){
                exportNoiThatTitle(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, cellId, thongTinNoiThat);
            }
            // If it's not roman, that mean it's the non merge row, we can call it content row
            else {
                exportNoiThatContent(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, cellId, thongTinNoiThat);
            }
            rowId++;
            mergeRowId++;
        }
    }
    private void exportNoiThatTitle(int mergeRowId, int mergeColumnId, int mergeRowRange, int mergeColumnRange, int cellId, ThongTinNoiThat thongTinNoiThat) {
        mergeCell(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange);
        createCustomCellForRomanCollum(mergeRowId, cellId, thongTinNoiThat.getSTT());
        createCustomCellForRomanCollum(mergeRowId, cellId + 1, thongTinNoiThat.getTenHangMuc());
        createCustomCellForRomanCollum(mergeRowId, cellId + 9, thongTinNoiThat.getThanhTien());
    }
    private void exportNoiThatContent(int mergeRowId, int mergeColumnId, int mergeRowRange, int mergeColumnRange, int cellId, ThongTinNoiThat thongTinNoiThat) {
        createCustomCellForRomanCollum(mergeRowId, cellId, thongTinNoiThat.getSTT());
        createCustomCellForRomanCollum(mergeRowId, cellId + 1, thongTinNoiThat.getTenHangMuc());
        createCustomCellForRomanCollum(mergeRowId, cellId + 2, thongTinNoiThat.getChiTiet());
        createCustomCellForRomanCollum(mergeRowId, cellId + 3, thongTinNoiThat.getDai());
        createCustomCellForRomanCollum(mergeRowId, cellId + 4, thongTinNoiThat.getRong());
        createCustomCellForRomanCollum(mergeRowId, cellId + 5, thongTinNoiThat.getCao());
        createCustomCellForRomanCollum(mergeRowId, cellId + 6, thongTinNoiThat.getDonGia());
        createCustomCellForRomanCollum(mergeRowId, cellId + 7, thongTinNoiThat.getDonViTinh());
        createCustomCellForRomanCollum(mergeRowId, cellId + 8, thongTinNoiThat.getSoLuong());
        createCustomCellForRomanCollum(mergeRowId, cellId + 9, thongTinNoiThat.getThanhTien());
    }
    private void createCustomCellForRomanCollum(int rowId, int cellId, String data) {
        Cell cell0 = spreadsheet.getRow(rowId).getCell(cellId);
        CellStyle cellStyle = stylistFactory.cellStyleFactory(Map.of(
                Stylist.Element.ALIGNMENT, Stylist.Style.Alignment_BOTH,
                Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
        ));
        cellStyle.setFont(stylistFactory.fontStyleFactory(Stylist.Style.Font_TimeNewRoman_BOLD, 14));
        cell0.setCellValue(data.toUpperCase());
        cell0.setCellStyle(cellStyle);
    }
    private Row createPopulatedRow(int rowId, int num) {
        Row newRow = spreadsheet.createRow(rowId);
        for (int i = 0; i < num; i++) {
            newRow.createCell(i);
        }
        return newRow;
    }
    public void exportLogo(InputStream image) throws IOException {
        byte[] bytes = image.readAllBytes();
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        XSSFDrawing drawing = spreadsheet.createDrawingPatriarch();
        XSSFClientAnchor logoAnchor = new XSSFClientAnchor();

        logoAnchor.setCol1(0);
        logoAnchor.setRow1(0);
        logoAnchor.setCol2(2);
        logoAnchor.setRow2(5);

        drawing.createPicture(logoAnchor, pictureIdx);
        image.close();
    }

    private void save() throws IOException {
        workbook.write(this.outputFile);
        workbook.close();
    }
}
