package com.huy.appnoithat.Service.FileExport;

import com.huy.appnoithat.DataModel.DataPackage;

import java.io.File;
import java.io.IOException;

public interface ExportFile {
    void export(File exportDirectory) throws IOException;
    void setUpDataForExport(DataPackage dataForExport);
    DataPackage importData(File importDirectory);
}
