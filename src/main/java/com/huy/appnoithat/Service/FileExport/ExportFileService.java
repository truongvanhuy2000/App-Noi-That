package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Entity.UserSelection;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface ExportFileService {
    public void writeInformationToFile(String fileName, List<AccountInformation> accountList) throws Exception;

    public void writeProductToFile(String fileName, List<UserSelection> listSelection) throws Exception;
    Font customFontExcel(int fontHeight, boolean isBold, boolean isItalic, String fontName, XSSFWorkbook workbook);

    CellStyle customCellStyle(boolean alignCenter, boolean border,CellStyle cellStyle);
}
