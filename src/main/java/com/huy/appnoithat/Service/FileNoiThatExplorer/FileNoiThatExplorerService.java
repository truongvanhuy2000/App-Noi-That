package com.huy.appnoithat.Service.FileNoiThatExplorer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.FileNoiThatExplorer.RecentFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileNoiThatExplorerService {
    final static Logger LOGGER = LogManager.getLogger(FileNoiThatExplorerService.class);
    private static final String RECENT_FILE_DIRECTORY = Config.FILE_EXPORT.RECENT_NT_FILE_DIRECTORY;
    private final ObjectMapper objectMapper;
    private static FileNoiThatExplorerService instance;
    public static synchronized FileNoiThatExplorerService getInstance() {
        if (instance == null) {
            instance = new FileNoiThatExplorerService();
        }
        return instance;
    }
    private FileNoiThatExplorerService() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
    public List<RecentFile> getRecentFile() {
        try {
            return objectMapper.readValue(new File(RECENT_FILE_DIRECTORY),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, RecentFile.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
