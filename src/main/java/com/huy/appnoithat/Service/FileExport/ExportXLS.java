package com.huy.appnoithat.Service.FileExport;


import com.huy.appnoithat.Entity.AccountInformation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExportXLS implements ExportFileService {


    @Override
    public void writeCountryListToFile(String fileName, List<AccountInformation> accountList) throws Exception {


        FileInputStream fis = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet spreadsheet = workbook.getSheetAt(0);

        Iterator<AccountInformation> iterator = accountList.iterator();

        int rowIndex = 0;
        while(iterator.hasNext()){
            AccountInformation inf = iterator.next();

            //setup font to display information customer
            Font font = customFontExcel(13,true,false,"Times New Roman",workbook);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(font);

            //colum and row of name customer
            Row rowA7 = spreadsheet.createRow(6); //row A7
            Cell cellA7 = rowA7.createCell(0);// cot A
            cellA7.setCellValue("Khách hàng : " + inf.getName());
            cellA7.setCellStyle(cellStyle);

            //colum and row of phone customer
            Row rowA8 = spreadsheet.createRow(7); //row A8
            Cell cellA8 = rowA8.createCell(0);// cot A
            cellA8.setCellValue("Điện thoại: " + inf.getPhone());
            cellA8.setCellStyle(cellStyle);


            //colum and row of address customer
            Row rowA9 = spreadsheet.createRow(8); //row A9
            Cell cellA9 = rowA9.createCell(0);// cot A
            cellA9.setCellValue("Địa chỉ: " + inf.getAddress());
            cellA9.setCellStyle(cellStyle);

            //colum and row of date customer
            Row rowA10 = spreadsheet.createRow(9); //row A10
            Cell cellA10 = rowA10.createCell(0);// cot A
            Date currentDate = new Date();
            // Định dạng ngày thành chuỗi "dd/MM/yyyy"
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = sdf.format(currentDate);
            cellA10.setCellValue("Ngày: " + formattedDate);
            cellA10.setCellStyle(cellStyle);


            // colum and row of Sản phẩm customer
//            Row rowA11 = spreadsheet.createRow(9); //row A11
//            Cell cellA11 = rowA11.createCell(0);// cot A
//            cellA11.setCellValue("Sản phẩm:  ");
//            cellA11.setCellStyle(cellStyle);

        }

        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        System.out.println(fileName + " written successfully");
    }

    @Override
    public Font customFontExcel(int fontHeight, boolean isBold, boolean isItalic, String fontName, XSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) fontHeight); // Kích thước chữ 13
        font.setBold(isBold); // set font Bold
        font.setItalic(isItalic);
        font.setFontName(fontName);
        return font;
    }
}
