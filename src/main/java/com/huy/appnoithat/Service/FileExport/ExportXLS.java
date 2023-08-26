package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Shared.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class ExportXLS implements ExportFileService {
    // USED FOR TESTING ONLY
    private static final String DEFAULT_TEMPLATE_PATH = "/template.xlsx";
    private static final String DEFAULT_OUTPUT_PATH = "target/object_collection_output.xlsx";

    private ThongTinCongTy thongTinCongTy;
    private ThongTinKhachHang thongTinKhachHang;
    private List<PhongCachNoiThat> phongCachNoiThatList;

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
        int mergeColumnRange = 8;
        int mergeColumnId = 0;
        int mergeRowId = 13;
        int mergeRowRange = 0;

        int cellId = 0;
        int rowId = 13;

        for (ThongTinNoiThat thongTinNoiThat : thongTinNoiThats) {
            if (Utils.RomanNumber.isRoman(thongTinNoiThat.getSTT())){

            }else {

            }
        }
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
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<>();
        DataFormat df = wb.createDataFormat();

        CellStyle style;
        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("header_date", style);

        Font font1 = wb.createFont();
        font1.setBold(true);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font1);
        styles.put("cell_b", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font1);
        styles.put("cell_b_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setFont(font1);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_b_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_g", style);

        Font font2 = wb.createFont();
        font2.setColor(IndexedColors.BLUE.getIndex());
        font2.setBold(true);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font2);
        styles.put("cell_bb", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_bg", style);

        Font font3 = wb.createFont();
        font3.setFontHeightInPoints((short)14);
        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
        font3.setBold(true);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font3);
        style.setWrapText(true);
        styles.put("cell_h", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        styles.put("cell_normal", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        styles.put("cell_normal_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_normal_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setIndention((short)1);
        style.setWrapText(true);
        styles.put("cell_indented", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("cell_blue", style);

        return styles;
    }

    private static CellStyle createBorderedStyle(Workbook wb){
        BorderStyle thin = BorderStyle.THIN;
        short black = IndexedColors.BLACK.getIndex();

        CellStyle style = wb.createCellStyle();
        style.setBorderRight(thin);
        style.setRightBorderColor(black);
        style.setBorderBottom(thin);
        style.setBottomBorderColor(black);
        style.setBorderLeft(thin);
        style.setLeftBorderColor(black);
        style.setBorderTop(thin);
        style.setTopBorderColor(black);
        return style;
    }
}
