package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.Shared.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ExportXLS implements ExportFileService {
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
    public ExportXLS(InputStream inputTemplate, OutputStream outputFile) {
        this.inputTemplate = inputTemplate;
        this.outputFile = outputFile;
    }
    public ExportXLS() {
        try {
            this.inputTemplate = new FileInputStream(Objects.requireNonNull(ExportXLS.class.getResource(DEFAULT_TEMPLATE_PATH)).getFile());
            this.outputFile = new FileOutputStream(DEFAULT_OUTPUT_PATH);
            workbook = new XSSFWorkbook(inputTemplate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        spreadsheet = workbook.getSheet("Sheet1");
    }

    @Override
    public void export() throws IOException {
        exportThongTinCongTy(this.thongTinCongTy);
        exportLogo(this.thongTinCongTy.getLogo());
        exportThongTinKhachHang(this.thongTinKhachHang);
        exportNoiThat(this.thongTinNoiThatList);
    }
    private void mergeCell(int row, int col, int rowSpan, int colSpan) {
        spreadsheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(row, row + rowSpan, col, col + colSpan));
    }
    private void customWriteForInformation(String data, int row, int cell, int fontSize, CellStyle cellStyle) {
        Cell cell0 = spreadsheet.getRow(row).getCell(cell);
        int endPos = 0;
        if (data.contains(":")) {
            endPos = data.indexOf(":");
        }else {
            endPos = data.length();
        }
        cell0.setCellValue(getCustomTextString(
                new XSSFRichTextString(data),
                0, endPos,
                customFontExcel(fontSize, false, false, "Times New Roman"),
                customFontExcel(fontSize, true, false, "Times New Roman"))
        );
        cell0.setCellStyle(cellStyle);
    }
    private void exportThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        int mergeColumnRange = 7;
        int mergeColumnId = 2;
        int mergeRowId = 0;
        int mergeRowRange = 0;

        int cellId = 2;
        int rowId = 0;

        CellStyle cellStyle = customCellStyle(false, true, true);

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

        CellStyle cellStyle = customCellStyle(false, true, false);

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
            if (Utils.RomanNumber.isRoman(thongTinNoiThat.getSTT())){
                mergeCell(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange);
                createCustomCellForRomanCollum(mergeRowId, cellId, thongTinNoiThat.getSTT());
                createCustomCellForRomanCollum(mergeRowId, cellId + 1, thongTinNoiThat.getTenHangMuc());
                createCustomCellForRomanCollum(mergeRowId, cellId + 9, thongTinNoiThat.getThanhTien());
            }
            else {

            }
            rowId++;
            mergeRowId++;
        }
    }
    private void createCustomCellForRomanCollum(int rowId, int cellId, String data) {
        Cell cell0 = spreadsheet.getRow(rowId).getCell(cellId);
        CellStyle cellStyle = customCellStyle(true, true, true);
        cellStyle.setFont(customFontExcel(14, true, false, "Times New Roman"));
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

    public void save() throws IOException {
        workbook.write(this.outputFile);
        workbook.close();
    }

    public Font customFontExcel(int fontHeight, boolean isBold, boolean isItalic, String fontName) {
        Font font = this.workbook.createFont();
        font.setFontHeightInPoints((short) fontHeight); // Font height
        font.setBold(isBold); // Set font Bold
        font.setItalic(isItalic);
        font.setFontName(fontName);
        return font;
    }

    public CellStyle customCellStyle(boolean horizontalCenter, boolean verticalCenter, boolean border) {
        CellStyle cellStyle = workbook.createCellStyle();
        if (horizontalCenter) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        if (verticalCenter) {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        if (border) {
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private RichTextString getCustomTextString(RichTextString getCustomTextString, int startPos, int endPos, Font defaultFont, Font customFont) {
        getCustomTextString.applyFont(defaultFont);
        getCustomTextString.applyFont(startPos, endPos, customFont);
        return getCustomTextString;
    }
}
