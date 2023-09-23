package com.huy.appnoithat.Service.FileExport.Operation.NtFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Service.FileExport.ExportData.CommonExportData;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import com.huy.appnoithat.Service.FileExport.Operation.NtFile.ObjectModel.Metadata;
import com.huy.appnoithat.Service.FileExport.Operation.NtFile.ObjectModel.ObjectData;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;

@Getter
@Setter
public class ExportNtFile implements ExportFile {
    private CommonExportData dataForExport;
    private ObjectData objectData;
    private OutputStream outputFile;
    private ObjectMapper mapper;
    public ExportNtFile(File outputFile) {
        this.mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        try {
            this.outputFile = new FileOutputStream(outputFile.getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void export(){
        try {
            String outPutJson = mapper.writeValueAsString(objectData);
            outputFile.write(outPutJson.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setUpDataForExport(CommonExportData dataForExport) {
        this.dataForExport = dataForExport;
        this.objectData = new ObjectData(dataForExport, new Metadata("test file", LocalDate.now()));
    }

    @Override
    public void importData() {

    }


}
