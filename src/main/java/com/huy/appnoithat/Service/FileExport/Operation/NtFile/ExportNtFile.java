package com.huy.appnoithat.Service.FileExport.Operation.NtFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import com.huy.appnoithat.Service.FileExport.Operation.NtFile.ObjectModel.Metadata;
import com.huy.appnoithat.Service.FileExport.Operation.NtFile.ObjectModel.ObjectData;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Getter
@Setter
public class ExportNtFile implements ExportFile {
    private DataPackage dataForExport;
    private ObjectData objectData;
    private ObjectMapper mapper;

    private OutputStream outputFile;
    private InputStream inputStream;

    public ExportNtFile() {
        this.mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    private void setOutputFile(File outputFile) throws FileNotFoundException {
        if (!outputFile.getAbsolutePath().contains(".nt")) {
            this.outputFile = new FileOutputStream(outputFile.getAbsolutePath() + ".nt");
        } else {
            this.outputFile = new FileOutputStream(outputFile.getAbsolutePath());
        }
    }

    @Override
    public void export(File exportDirectory) throws IOException {
        setOutputFile(exportDirectory);
        try {
            String outPutJson = mapper.writeValueAsString(objectData);
            String encodedString = Utils.encodeData(outPutJson);
            this.outputFile.write(encodedString.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setUpDataForExport(DataPackage dataForExport) {
        this.dataForExport = dataForExport;
        this.objectData = new ObjectData(dataForExport, new Metadata("test file", LocalDate.now()));
    }

    @Override
    public DataPackage importData(File importDirectory) {
        ObjectData objectData1;
        try (InputStream inputStream = new FileInputStream(importDirectory)) {
            String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String decodedString = Utils.decodeData(text);
            objectData1 = mapper.readValue(decodedString.getBytes(), ObjectData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objectData1.getDataPackage();
    }
}
