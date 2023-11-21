package com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.Excel;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.ItemTypeUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ExportXLS {
    protected static final String DEFAULT_TEMPLATE_PATH = Config.FILE_EXPORT.XLSX_TEMPLATE_DIRECTORY;
    protected static final String DEFAULT_OUTPUT_PATH = Config.FILE_EXPORT.XLSX_DEFAULT_OUTPUT_DIRECTORY;
    protected OutputStream outputFile;
    protected InputStream inputTemplate;
    protected XSSFWorkbook workbook;
    protected StylistFactory stylistFactory;
    protected void exportNoteArea(XSSFSheet spreadsheet, int rowId, String noteArea) {
        Cell cell0 = spreadsheet.getRow(rowId).getCell(0);
        stylistFactory.CellPresetFactory(cell0, noteArea, 13, Stylist.Preset.NormalText_TimeNewRoman_VerticalCenter_ThinBorder);
    }
    protected void mergeCells(XSSFSheet spreadsheet, int row, int col, int rowSpan, int colSpan, int howMany) {
        for (int i = 0; i < howMany; i++) {
            spreadsheet.addMergedRegion(new CellRangeAddress(row + i, row + rowSpan + i, col, col + colSpan));
        }
    }
    protected void setOutputFile(File outputFile) throws FileNotFoundException {
        if (!outputFile.getAbsolutePath().contains(".xlsx")) {
            this.outputFile = new FileOutputStream(outputFile.getAbsolutePath() + ".xlsx");
        } else {
            this.outputFile = new FileOutputStream(outputFile.getAbsolutePath());
        }
    }
    protected List<ThongTinNoiThat> setThongTinNoiThatList(List<ThongTinNoiThat> thongTinNoiThatList) {
        List<ThongTinNoiThat> thongTinForExport = new ArrayList<>();
        thongTinNoiThatList.forEach(item -> {
            String stt = item.getSTT();
            if (ItemTypeUtils.determineItemType(stt) == ItemType.NUMERIC ||
                    ItemTypeUtils.determineItemType(stt) == ItemType.ROMAN) {
                thongTinForExport.add(item);
            }
        });
        rearrangeList(thongTinForExport);
        return thongTinForExport;
    }
    protected void rearrangeList(List<ThongTinNoiThat> list) {
        int romanCount = 0;
        for (ThongTinNoiThat item : list) {
            if (Objects.requireNonNull(ItemTypeUtils.determineItemType(item.getSTT())) == ItemType.ROMAN) {
                romanCount++;
                item.setSTT(ItemTypeUtils.createFullId(ItemType.ROMAN, Utils.toRoman(romanCount)));
            }
        }
    }
    protected ThongTinCongTy setThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        ThongTinCongTy thongTinForExport = new ThongTinCongTy();
        thongTinForExport.setLogo(thongTinCongTy.getLogo());
        thongTinForExport.setTenCongTy(thongTinCongTy.getTenCongTy());
        thongTinForExport.setDiaChiVanPhong("Địa chỉ văn phòng: " + thongTinCongTy.getDiaChiVanPhong());
        thongTinForExport.setDiaChiXuong("Địa chỉ nhà xưởng: " + thongTinCongTy.getDiaChiXuong());
        thongTinForExport.setSoDienThoai("Hotline: " + thongTinCongTy.getSoDienThoai());
        thongTinForExport.setEmail("Email: " + thongTinCongTy.getEmail());
        return thongTinForExport;
    }
    protected ThongTinKhachHang setThongTinKhachHang(ThongTinKhachHang thongTinKhachHang) {
        ThongTinKhachHang thongTinForExport = new ThongTinKhachHang();
        thongTinForExport.setTenKhachHang("Khách hàng : " + thongTinKhachHang.getTenKhachHang());
        thongTinForExport.setDiaChi("Địa chỉ: " + thongTinKhachHang.getDiaChi());
        thongTinForExport.setSoDienThoai("Điện thoại: " + thongTinKhachHang.getSoDienThoai());
        thongTinForExport.setDate("Ngày: " + thongTinKhachHang.getDate());
        thongTinForExport.setSanPham("Sản phẩm: " + thongTinKhachHang.getSanPham());
        return thongTinForExport;
    }
    protected void exportThongTinCongTy(XSSFSheet spreadsheet, ThongTinCongTy thongTinCongTy) {
        int mergeColumnRange = 7;
        int mergeColumnId = 2;
        int mergeRowId = 0;
        int mergeRowRange = 0;

        int cellId = 2;
        int rowId = 0;

        mergeCells(spreadsheet, mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, 5);

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

    protected void exportThongTinKhachHang(XSSFSheet spreadsheet, ThongTinKhachHang thongTinKhachHang) {
        int mergeColumnRange = 9;
        int mergeColumnId = 0;
        int mergeRowId = 6;
        int mergeRowRange = 0;

        int cellId = 0;
        int rowId = 6;

        mergeCells(spreadsheet, mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, 5);

        Cell cell0 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell0, thongTinKhachHang.getTenKhachHang(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_NoBorder);

        Cell cell1 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell1, thongTinKhachHang.getDiaChi(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_NoBorder);

        Cell cell2 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell2, thongTinKhachHang.getSoDienThoai(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_NoBorder);

        Cell cell3 = spreadsheet.getRow(rowId++).getCell(cellId);
        stylistFactory.CellPresetFactory(cell3, thongTinKhachHang.getDate(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_NoBorder);

        Cell cell4 = spreadsheet.getRow(rowId).getCell(cellId);
        stylistFactory.CellPresetFactory(cell4, thongTinKhachHang.getSanPham(), 13, Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_NoBorder);
    }

    protected int exportNoiThat(XSSFSheet spreadsheet, List<ThongTinNoiThat> thongTinNoiThats) {
        int mergeColumnRange = 7;
        int mergeColumnId = 1;
        int mergeRowId = 13;
        int mergeRowRange = 0;

        int cellId = 0;
        int rowId = 13;

        for (ThongTinNoiThat thongTinNoiThat : thongTinNoiThats) {
            if (thongTinNoiThat.getSTT().isEmpty() ||
                    thongTinNoiThat.getTenHangMuc().isEmpty()) {
                continue;
            }
            spreadsheet.shiftRows(rowId, spreadsheet.getLastRowNum(), 1, true, true);
            Row newRow = createPopulatedRow(spreadsheet, rowId, 10);
            // If STT is roman, that mean it's the merge Row, we can call it title row
            if (ItemTypeUtils.determineItemType(thongTinNoiThat.getSTT()) == ItemType.ROMAN) {
                exportNoiThatTitle(spreadsheet, mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, cellId, thongTinNoiThat);
            }
            // If it's not roman, that mean it's the non merge row, we can call it content row
            else {
                exportNoiThatContent(spreadsheet, mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, cellId, thongTinNoiThat);
            }
            rowId++;
            mergeRowId++;
        }
        return rowId;
    }

    protected void exportNoiThatTitle(XSSFSheet spreadsheet, int mergeRowId, int mergeColumnId, int mergeRowRange,
                                    int mergeColumnRange, int cellId, ThongTinNoiThat thongTinNoiThat) {
        mergeCells(spreadsheet, mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, 1);

        Cell cell0 = spreadsheet.getRow(mergeRowId).getCell(cellId);
        stylistFactory.CellPresetFactory(cell0, ItemTypeUtils.getIdFromFullId(thongTinNoiThat.getSTT()),
                12, Stylist.Preset.BoldAll_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell1 = spreadsheet.getRow(mergeRowId).getCell(cellId + 1);
        stylistFactory.CellPresetFactory(cell1, thongTinNoiThat.getTenHangMuc(),
                14, Stylist.Preset.BoldAll_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell2 = spreadsheet.getRow(mergeRowId).getCell(cellId + 9);
        stylistFactory.CellPresetFactory(cell2, thongTinNoiThat.getThanhTien(),
                18, Stylist.Preset.BoldAll_TimeNewRoman_CenterBoth_ThinBorder);
    }

    protected void exportNoiThatContent(XSSFSheet spreadsheet, int mergeRowId, int mergeColumnId, int mergeRowRange,
                                      int mergeColumnRange, int cellId, ThongTinNoiThat thongTinNoiThat) {
        Cell cell0 = spreadsheet.getRow(mergeRowId).getCell(cellId);
        stylistFactory.CellPresetFactory(cell0, ItemTypeUtils.getIdFromFullId(thongTinNoiThat.getSTT()), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell1 = spreadsheet.getRow(mergeRowId).getCell(cellId + 1);
        stylistFactory.CellPresetFactory(cell1, thongTinNoiThat.getTenHangMuc(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell2 = spreadsheet.getRow(mergeRowId).getCell(cellId + 2);
        stylistFactory.CellPresetFactory(cell2, thongTinNoiThat.getChiTiet(), 12, Stylist.Preset.BoldText03_TimeNewRoman_VerticalCenter_ThinBorder);

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

    protected void exportBangThanhToan(XSSFSheet spreadsheet, int rowId, ThongTinThanhToan thongTinThanhToan) {
        int mergeRowId = rowId;

        int cellId = 0;

//        mergeCells(mergeRowId, mergeColumnId, mergeRowRange, mergeColumnRange, 1);

        Cell cell0 = spreadsheet.getRow(mergeRowId).getCell(cellId);
        stylistFactory.CellPresetFactory(cell0, thongTinThanhToan.getTongTien(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell1 = spreadsheet.getRow(mergeRowId).getCell(cellId + 2);
        stylistFactory.CellPresetFactory(cell1, thongTinThanhToan.getDatCocThietKe10(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell2 = spreadsheet.getRow(mergeRowId).getCell(cellId + 3);
        stylistFactory.CellPresetFactory(cell2, thongTinThanhToan.getDatCocThiCong30(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell3 = spreadsheet.getRow(mergeRowId).getCell(cellId + 6);
        stylistFactory.CellPresetFactory(cell3, thongTinThanhToan.getHangDenChanCongTrinh50(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);

        Cell cell4 = spreadsheet.getRow(mergeRowId).getCell(cellId + 8);
        stylistFactory.CellPresetFactory(cell4, thongTinThanhToan.getNghiemThuQuyet(), 12, Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder);
    }

    protected Row createPopulatedRow(XSSFSheet spreadsheet, int rowId, int num) {
        Row newRow = spreadsheet.createRow(rowId);
        for (int i = 0; i < num; i++) {
            newRow.createCell(i);
        }
        return newRow;
    }

    protected void exportLogo(XSSFSheet spreadsheet, InputStream image) throws IOException {
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

    protected void save() throws IOException {
        workbook.write(this.outputFile);
        workbook.close();
        outputFile.close();
    }
}
