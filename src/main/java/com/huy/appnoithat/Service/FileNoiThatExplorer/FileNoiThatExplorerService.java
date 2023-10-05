package com.huy.appnoithat.Service.FileNoiThatExplorer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.FileNoiThatExplorer.RecentFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<RecentFile> recentFileObservableList;
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
    public ObservableList<RecentFile> getRecentFile() {
        if (recentFileObservableList == null) {
            try {
                List<RecentFile> recentFileList = objectMapper.readValue(new File(RECENT_FILE_DIRECTORY),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, RecentFile.class));
                recentFileObservableList = FXCollections.observableArrayList(recentFileList);
                return recentFileObservableList;
            } catch (IOException e) {
                LOGGER.error("Failed to read recent file" + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else {
            return this.recentFileObservableList;
        }
    }
    public void saveRecentFile() {
        try {
            List<RecentFile> recentFileList = this.recentFileObservableList.stream().toList();
            objectMapper.writeValue(new File(RECENT_FILE_DIRECTORY), recentFileList);
        } catch (IOException e) {
            LOGGER.error("Failed to write recent file" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void addRecentFile(RecentFile recentFile) {
        if (recentFileObservableList == null) {
            recentFileObservableList = getRecentFile();
        }
        if (isFileExist(recentFile)) {
            return;
        }
        recentFileObservableList.add(recentFile);
        saveRecentFile();
    }
    public void removeRecentFile(RecentFile recentFile) {
        if (recentFileObservableList == null) {
            recentFileObservableList = getRecentFile();
        }
        recentFileObservableList.remove(recentFile);
        saveRecentFile();
    }
    private boolean isFileExist(RecentFile recentFile) {
        return recentFileObservableList.stream().anyMatch(recentFile1 -> recentFile1.getDirectory().equals(recentFile.getDirectory()));
    }
}
