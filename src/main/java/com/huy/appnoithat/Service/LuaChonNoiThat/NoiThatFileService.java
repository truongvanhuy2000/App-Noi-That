package com.huy.appnoithat.Service.LuaChonNoiThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.FileNoiThatExplorer.RecentFile;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.DataModel.SaveFile.Metadata;
import com.huy.appnoithat.DataModel.SaveFile.ObjectData;
import com.huy.appnoithat.DataModel.SaveFile.TabData;
import com.huy.appnoithat.Service.FileNoiThatExplorer.FileNoiThatExplorerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class NoiThatFileService {
    final static Logger LOGGER = LogManager.getLogger(NoiThatFileService.class);
    private ObjectData objectData;
    private ObjectMapper mapper;
    FileNoiThatExplorerService fileNoiThatExplorerService;
    private OutputStream outputFile;

    public NoiThatFileService() {
        this.mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        fileNoiThatExplorerService = FileNoiThatExplorerService.getInstance();
    }

    private void setOutputFile(File outputFile) throws FileNotFoundException {
        if (!outputFile.getAbsolutePath().contains(".nt")) {
            this.outputFile = new FileOutputStream(outputFile.getAbsolutePath() + ".nt");
        } else {
            this.outputFile = new FileOutputStream(outputFile.getAbsolutePath());
        }
    }

    public void export(List<TabData> dataForExport, File exportDirectory) {
        try {
            setUpDataForExport(dataForExport);
            setOutputFile(exportDirectory);
            String outPutJson = mapper.writeValueAsString(objectData);
            String encodedString = Utils.encodeData(outPutJson);
            outputFile.write(encodedString.getBytes());
            outputFile.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        fileNoiThatExplorerService.addRecentFile(new RecentFile(exportDirectory.getAbsolutePath(), System.currentTimeMillis()));
    }

    public void setUpDataForExport(List<TabData> dataForExport) {
        this.objectData = new ObjectData(dataForExport, new Metadata("test file", LocalDate.now()));
    }

    public List<TabData> importData(String importDirectory) {
        ObjectData objectData1;
        try (InputStream inputStream = new FileInputStream(importDirectory)) {
            String decodedString = Utils.decodeData(inputStream.readAllBytes());
//            LOGGER.debug(decodedString);
            objectData1 = mapper.readValue(decodedString, ObjectData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objectData1.getExportData();
    }
}
