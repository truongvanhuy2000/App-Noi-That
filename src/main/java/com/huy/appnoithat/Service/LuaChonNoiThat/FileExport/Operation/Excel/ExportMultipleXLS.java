package com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.Excel;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.ItemTypeUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.ExportFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ExportMultipleXLS extends ExportXLS {
    final static Logger LOGGER = LogManager.getLogger(ExportMultipleXLS.class);

    public ExportMultipleXLS() {
        try {
            this.inputTemplate = new FileInputStream(DEFAULT_TEMPLATE_PATH);
            initWorkbook();
        } catch (IOException e) {
            LOGGER.error("Error while init ExportXLS");
            throw new RuntimeException(e);
        }
    }
}
