package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.Entity.AccountInformation;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface ExportFileService {
    public void writeCountryListToFile(String fileName, List<AccountInformation> accountList) throws Exception;


    Font customFontExcel(int fontHeight, boolean isBold, boolean isItalic, String fontName, XSSFWorkbook workbook);
}
