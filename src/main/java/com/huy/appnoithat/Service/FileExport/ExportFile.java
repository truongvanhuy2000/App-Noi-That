package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.Service.FileExport.ExportData.CommonExportData;

import java.io.IOException;

public interface ExportFile {
    void export() throws IOException;
    void setUpDataForExport(CommonExportData dataForExport);
    void importData();
}
