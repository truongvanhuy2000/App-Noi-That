package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.Enums.FileType;
import com.huy.appnoithat.Service.FileExport.Operation.Excel.ExportXLS;
import com.huy.appnoithat.Service.FileExport.Operation.PDF.ExportPDF;

import java.io.File;

public class FileExportService {
    public ExportFile getExportService(File outputFile, FileType fileType) {
        return switch (fileType) {
            case EXCEL -> new ExportXLS();
            case PDF -> new ExportPDF();
            default -> null;
        };
    }
}
