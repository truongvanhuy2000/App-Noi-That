package com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.PDF;
//import com.groupdocs.conversion.Converter;
//import com.groupdocs.conversion.options.convert.PdfConvertOptions;

import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.ExportFile;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.Excel.ExportSingleXLS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExportPDF implements ExportFile {
    final static Logger LOGGER = LogManager.getLogger(ExportPDF.class);
    ExportSingleXLS exportSingleXLS = new ExportSingleXLS();
    private File outputFile;

    @Override
    public void export(File exportDirectory) throws IOException {
        setOutputFile(exportDirectory);
        String tempXlsxFile = "temp" + System.currentTimeMillis() + ".xlsx";
        String xlsFileDir = Paths.get(Config.FILE_EXPORT.XLSX_DEFAULT_OUTPUT_DIRECTORY, tempXlsxFile).toAbsolutePath().toString();
        exportSingleXLS.export(new File(xlsFileDir));
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
        exportSingleXLS.setUpDataForExport(dataForExport);
    }

    public void convertXLStoPDF(String directory){
        String tempPdfFile = "temp" + System.currentTimeMillis() + ".pdf";
        String tempPdfPath = Paths.get(Config.ROOT_DIRECTORY, tempPdfFile).toAbsolutePath().toString();
        try {
            //read all the lines of the .vbs script into memory as a list
            //here we pull from the resources of a Gradle build, where the vbs script is stored
            Path tempScript = Files.createTempFile("script", ".vbs");
            List<String> script = Files.readAllLines(Paths.get(Config.FILE_EXPORT.VBS_SCRIPT_DIRECTORY));
            script = script.stream().map(line -> {
                if (line.contains("EXEL_PATH")) {
                    line = line.replace("EXEL_PATH", directory);
                }
                if (line.contains("PDF_PATH")) {

                    line = line.replace("PDF_PATH", tempPdfPath);
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
            Files.move(Paths.get(tempPdfPath), Paths.get(outputFile.getAbsolutePath()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
