package com.huy.appnoithat.Service.FileExport.Operation.PDF;

import com.huy.appnoithat.DataModel.NtFile.DataPackage;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import com.huy.appnoithat.Service.FileExport.Operation.Excel.ExportXLS;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

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
        Workbook workbook = new Workbook();
        workbook.loadFromFile(directory);

        //Set worksheets to fit to width when converting
        workbook.getConverterSetting().setSheetFitToWidth(true);

        //Get the first worksheet
        Worksheet worksheet = workbook.getWorksheets().get(0);

        //Convert to PDF and save the resulting document to a specified path
        worksheet.saveToPdf(outputFile.getAbsolutePath());
    }
}
