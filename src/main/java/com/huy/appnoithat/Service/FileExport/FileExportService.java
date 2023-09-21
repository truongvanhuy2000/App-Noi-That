package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.Enums.FileType;
import com.huy.appnoithat.Service.FileExport.Operation.Excel.ExportXLS;
import com.huy.appnoithat.Service.FileExport.Operation.NtFile.ExportNtFile;

import java.io.File;

public class FileExportService {
    public ExportFile getExportService(File outputFile, FileType fileType) {
        return switch (fileType) {
            case EXCEL -> new ExportXLS(outputFile);
            case NT -> new ExportNtFile(outputFile);
            default -> null;
        };
    }
}
