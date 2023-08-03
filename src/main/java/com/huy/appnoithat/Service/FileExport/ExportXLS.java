package com.huy.appnoithat.Service.FileExport;


import com.huy.appnoithat.Entity.*;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExportXLS implements ExportFileService {


    @Override
    public void writeInformationToFile(String fileName, List<AccountInformation> accountList) throws Exception {


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
    public void writeProductToFile(String fileName, List<UserSelection> listSelection) throws Exception {
        FileInputStream fis = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet spreadsheet = workbook.getSheetAt(0);


        //setup font to display title product
        Font fontTitleProduct = customFontExcel(14,true,false,"Times New Roman",workbook);
        CellStyle cellStyleTitleProduct = workbook.createCellStyle();
        cellStyleTitleProduct.setFont(fontTitleProduct);
        cellStyleTitleProduct = customCellStyle(true,true,cellStyleTitleProduct);

        CellRangeAddress rangeAddress = new CellRangeAddress(13, 13, 1, 8);

        // Ghi văn bản vào mỗi ô trong phạm vi đã xác định
        /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  write title product    */
        for(UserSelection selection : listSelection) {
            Row row = spreadsheet.createRow(rangeAddress.getFirstRow());
            for (int col = rangeAddress.getFirstColumn(); col <= rangeAddress.getLastColumn(); col++) {
                Cell cell = row.createCell(col);
                cell.setCellValue(listSelection.get(0).getPhongCachNoiThat().getName());
                cell.setCellStyle(cellStyleTitleProduct);
            }

            /*write list product*/
        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> write product data*/
            int rowid = 14; //hang 15
            int cellid = 1; // cot B

            //This data needs to be written (Object[])
            Map < String, Object[] > productinfo = new TreeMap < String, Object[] >();
            productinfo.put( "1", new Object[] { "Tủ bếp long", "- Thùng  : MDF chống ẩm an cường , độ dày 18mm ", "189","350","750","mét dài","2,360,000","1.00"," 29,055,000" });
            productinfo.put( "2", new Object[] { "Tủ bếp linh2", "- Thùng  : MDF chống ẩm an cường , độ dày 18mm ", "200","450","850","mét dài","3,360,000","2.00"," 30,055,000" });

            //Iterate over data and write to sheet
            Set < String > keyid = productinfo.keySet();
            CellStyle cellStyleEachProduct = workbook.createCellStyle();
            Font fontTextProduct = customFontExcel(12,false,false,"Times New Roman",workbook);
            cellStyleEachProduct.setFont(fontTextProduct);

            for (String key : keyid) {
                row = spreadsheet.createRow(rowid++);
                Object [] objectArr = productinfo.get(key);
                for (Object obj : objectArr) {
                    if(cellid==10){
                        cellid=1;
                    }
                    Cell cellProduct = row.createCell(cellid++);
                    cellProduct.setCellValue((String)obj);
                    cellProduct.setCellStyle(customCellStyle(true,true,cellStyleEachProduct));
                }
            }



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

    @Override
    public CellStyle customCellStyle(boolean alignCenter, boolean border,CellStyle cellStyle) {
        if(alignCenter){
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        if(border){
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }
        return cellStyle;
    }

}
