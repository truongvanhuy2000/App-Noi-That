package com.huy.appnoithat.Service.LuaChonNoiThat.FileExport;

import com.huy.appnoithat.Enums.FileType;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.Excel.ExportSingleXLS;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.PDF.ExportPDF;

import java.io.File;

public class FileExportService {
    public ExportFile getExportService(File outputFile, FileType fileType) {
        return switch (fileType) {
            case EXCEL -> new ExportSingleXLS();
            case PDF -> new ExportPDF();
            default -> null;
        };
    }
}
