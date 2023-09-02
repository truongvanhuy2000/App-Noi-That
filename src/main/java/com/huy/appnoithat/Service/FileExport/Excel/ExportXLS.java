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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
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
    private void mergeCells(int row, int col, int rowSpan, int colSpan, int howMany) {
        for (int i = 0; i < howMany; i++) {
            spreadsheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(row + i, row + rowSpan + i, col, col + colSpan));
        }
    }
    private void exportThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        int mergeColumnRange = 7;
        int mergeColumnId = 2;
        int mergeRowId = 0;
        int mergeRowRange = 0;

        int cellId = 2;
        int rowId = 0;

        mergeCells(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, 5);

        Cell cell0 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell0, thongTinCongTy.getTenCongTy(), 18, Stylist.Preset.BoldAll_TimeNewRoman_VerticalCenter_ThinBorder);

        Cell cell1 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell1, thongTinCongTy.getDiaChiVanPhong(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder);

        Cell cell2 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell2, thongTinCongTy.getDiaChiXuong(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder);

        Cell cell3 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell3, thongTinCongTy.getSoDienThoai(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder);

        Cell cell4 = spreadsheet.getRow(rowId).getCell(cellId);
        stylistFactory.CellPresetFactory(cell4, thongTinCongTy.getEmail(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder);
    }

    private void exportThongTinKhachHang(ThongTinKhachHang thongTinKhachHang) {
        int mergeColumnRange = 9;
        int mergeColumnId = 0;
        int mergeRowId = 6;
        int mergeRowRange = 0;

        int cellId = 0;
        int rowId = 6;

        mergeCells(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, 5);

        Cell cell0 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell0, thongTinKhachHang.getTenKhachHang(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder);

        Cell cell1 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell1, thongTinKhachHang.getDiaChi(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder);

        Cell cell2 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell2, thongTinKhachHang.getSoDienThoai(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder);

        Cell cell3 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell3, thongTinKhachHang.getDate(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder);

        Cell cell4 = spreadsheet.getRow(rowId).getCell(cellId);
        stylistFactory.CellPresetFactory(cell4, thongTinKhachHang.getSanPham(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder);
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
        mergeCells(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, 1);

        Cell cell0 = spreadsheet.getRow(mergeRowId).getCell(cellId);
        stylistFactory.CellPresetFactory(cell0, thongTinNoiThat.getSTT(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell1 = spreadsheet.getRow(mergeRowId).getCell(cellId + 1);
        stylistFactory.CellPresetFactory(cell1, thongTinNoiThat.getTenHangMuc(), 14, Stylist.Preset.BoldAll_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell2 = spreadsheet.getRow(mergeRowId).getCell(cellId + 9);
        stylistFactory.CellPresetFactory(cell2, thongTinNoiThat.getThanhTien(), 18, Stylist.Preset.BoldAll_TimeNewRoman_CenterBoth_ThinBorder);
    }
    private void exportNoiThatContent(int mergeRowId, int mergeColumnId, int mergeRowRange, int mergeColumnRange, int cellId, ThongTinNoiThat thongTinNoiThat) {
        Cell cell0 = spreadsheet.getRow(mergeRowId).getCell(cellId);
        stylistFactory.CellPresetFactory(cell0, thongTinNoiThat.getSTT(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell1 = spreadsheet.getRow(mergeRowId).getCell(cellId + 1);
        stylistFactory.CellPresetFactory(cell1, thongTinNoiThat.getTenHangMuc(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell2 = spreadsheet.getRow(mergeRowId).getCell(cellId + 2);
        stylistFactory.CellPresetFactory(cell2, thongTinNoiThat.getChiTiet(), 12, Stylist.Preset.BoldText01_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell3 = spreadsheet.getRow(mergeRowId).getCell(cellId + 3);
        stylistFactory.CellPresetFactory(cell3, thongTinNoiThat.getDai(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell4 = spreadsheet.getRow(mergeRowId).getCell(cellId + 4);
        stylistFactory.CellPresetFactory(cell4, thongTinNoiThat.getRong(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell5 = spreadsheet.getRow(mergeRowId).getCell(cellId + 5);
        stylistFactory.CellPresetFactory(cell5, thongTinNoiThat.getCao(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell6 = spreadsheet.getRow(mergeRowId).getCell(cellId + 6);
        stylistFactory.CellPresetFactory(cell6, thongTinNoiThat.getDonViTinh(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell7 = spreadsheet.getRow(mergeRowId).getCell(cellId + 7);
        stylistFactory.CellPresetFactory(cell7, thongTinNoiThat.getDonGia(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell8 = spreadsheet.getRow(mergeRowId).getCell(cellId + 8);
        stylistFactory.CellPresetFactory(cell8, thongTinNoiThat.getSoLuong(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell9 = spreadsheet.getRow(mergeRowId).getCell(cellId + 9);
        stylistFactory.CellPresetFactory(cell9, thongTinNoiThat.getThanhTien(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);
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
