package com.huy.appnoithat.Service.FileExport.Operation.PDF;
//import com.groupdocs.conversion.Converter;
//import com.groupdocs.conversion.options.convert.PdfConvertOptions;

import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import com.huy.appnoithat.Service.FileExport.Operation.Excel.ExportXLS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public void convertXLStoPDF(String directory){
        try {
            //read all the lines of the .vbs script into memory as a list
            //here we pull from the resources of a Gradle build, where the vbs script is stored
            Path tempScript = Files.createTempFile("script", ".vbs");
            List<String> script = Files.readAllLines(Paths.get(Config.FILE_EXPORT.VBS_SCRIPT_DIRECTORY));

//            // append test.xlsm for file name. savePath was passed to this function
//            String templateFile = directory;
////            templateFile = templateFile.replace("\\", "\\\\");
//            String pdfFile = outputFile.getAbsolutePath();
////            pdfFile = pdfFile.replace("\\", "\\\\");
//            System.out.println("templateFile is: " + templateFile);
//            System.out.println("pdfFile is: " + pdfFile);
//
//            //replace the placeholders in the vbs script with the chosen file paths
            script = script.stream().map(line -> {
                if (line.contains("EXEL_PATH")) {
                    line = line.replace("EXEL_PATH", directory);
                }
                if (line.contains("PDF_PATH")) {
                    line = line.replace("PDF_PATH", outputFile.getAbsolutePath());
                }
                return line;
            }).toList();
            Files.write(tempScript, script);
            //create a processBuilder for starting an operating system process
            ProcessBuilder pb = new ProcessBuilder("wscript", tempScript.toString());

            //start the process on the operating system
            Process process = pb.start();

            //tell the process how long to wait for timeout
            boolean success = process.waitFor(2, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
