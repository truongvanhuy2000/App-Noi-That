package com.huy.appnoithat.Service.FileExport.Operation.PDF;
//import com.groupdocs.conversion.Converter;
//import com.groupdocs.conversion.options.convert.PdfConvertOptions;

import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import com.huy.appnoithat.Service.FileExport.Operation.Excel.ExportXLS;

import java.io.File;
import java.io.IOException;

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
//        try {
//            Converter converter = new Converter(directory);
//            PdfConvertOptions options = new PdfConvertOptions();
//
//            converter.convert("converted.pdf", options);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
