package com.huy.appnoithat.Service.FileExport.Operation.PDF;

import com.huy.appnoithat.DataModel.NtFile.DataPackage;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import com.huy.appnoithat.Service.FileExport.Operation.Excel.ExportXLS;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;

import java.io.*;

public class ExportPDF implements ExportFile {
    ExportXLS exportXLS = new ExportXLS();
    private File outputFile;

    @Override
    public void export(File exportDirectory) throws IOException {
        setOutputFile(exportDirectory);
        exportXLS.export(exportDirectory);
        String xlsFileDir = exportDirectory.getAbsolutePath() + ".xlsx";
        convertXLStoPDF(xlsFileDir);
        deleteExistingFile(new File(xlsFileDir));
    }
    private void deleteExistingFile(File file){
        if(file.exists()){
            file.delete();
        }
    }
    private void setOutputFile(File outputFile) {
        if (!outputFile.getAbsolutePath().contains(".pdf")) {
            this.outputFile = new File(outputFile.getAbsolutePath() + ".pdf");
        } else {
            this.outputFile = new File(outputFile.getAbsolutePath());
        }
    }
    @Override
    public void setUpDataForExport(DataPackage dataForExport) {
        exportXLS.setUpDataForExport(dataForExport);
    }

    @Override
    public DataPackage importData(File importDirectory) {
        return null;
    }
    private void convertXLStoPDF(String directory){
        try {
            Workbook workbook = new Workbook();
            workbook.loadFromFile(directory);
            workbook.getConverterSetting().setSheetFitToWidth(true);
            workbook.getConverterSetting().setYDpi(500);
            //Get the first worksheet
            //Convert to PDF and save the resulting document to a specified path
            workbook.saveToFile("output/test.pdf", FileFormat.PDF);
            workbook.dispose();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
