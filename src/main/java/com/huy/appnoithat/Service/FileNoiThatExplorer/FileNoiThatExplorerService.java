package com.huy.appnoithat.Service.FileNoiThatExplorer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.DataModel.RecentFile;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FileNoiThatExplorerService {
    final static Logger LOGGER = LogManager.getLogger(FileNoiThatExplorerService.class);
    private static final String RECENT_FILE_DIRECTORY = Config.FILE_EXPORT.RECENT_NT_FILE_DIRECTORY;
    private final ObjectMapper objectMapper;
    private static FileNoiThatExplorerService instance;
    private ObservableList<RecentFile> recentFileObservableList = FXCollections.observableArrayList();
    PersistenceStorageService persistenceStorageService;
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
        persistenceStorageService = PersistenceStorageService.getInstance();
    }

    /**
     * Gets the observable list of recent files. If the list is not initialized, it reads the data from the file.
     *
     * @return ObservableList of RecentFile objects.
     * @throws RuntimeException if there is an IOException while reading the file.
     */
    public ObservableList<RecentFile> getRecentFile() {
        recentFileObservableList.clear();
        List<RecentFile> recentFileList = persistenceStorageService.getRecentFileList();
        recentFileObservableList.addAll(recentFileList);
        return recentFileObservableList;
    }

    /**
     * Saves the recent files to the file system.
     *
     * @throws RuntimeException if there is an IOException while writing the file.
     */
    public void saveRecentFile() {
        // Convert the observable list to a regular list and write it to the file
        List<RecentFile> recentFileList = this.recentFileObservableList.stream().toList();
        persistenceStorageService.saveRecentFile(recentFileList);
    }

    /**
     * Adds a recent file to the list and saves the updated list to the file system.
     *
     * @param recentFile The recent file to be added.
     * @throws RuntimeException if there is an IOException while writing the file.
     */
    public void addRecentFile(RecentFile recentFile) {
        if (recentFileObservableList == null) {
            // Retrieve the recent file list if it's not already loaded
            recentFileObservableList = getRecentFile();
        }

        // Check if the file already exists in the list
        if (isFileExist(recentFile) != null) {
            return;
        }
        // Add the recent file to the list and save the updated list to the file system
        recentFileObservableList.add(recentFile);
        saveRecentFile();
    }
    /**
     * Removes a recent file from the list and saves the updated list to the file system.
     *
     * @param recentFile The recent file to be removed.
     * @throws RuntimeException if there is an IOException while writing the file.
     */
    public void removeRecentFile(RecentFile recentFile) {
        // Retrieve the recent file list if it's not already loaded
        if (recentFileObservableList == null) {
            recentFileObservableList = getRecentFile();
        }
        // Remove the recent file from the list and save the updated list to the file system
        recentFileObservableList.remove(recentFile);
        saveRecentFile();
    }

    /**
     * Checks if a recent file already exists in the recent file list.
     *
     * @param recentFile The recent file to be checked.
     * @return True if the recent file already exists in the list, false otherwise.
     */
    private RecentFile isFileExist(RecentFile recentFile) {
        return recentFileObservableList.stream().filter(
                recentFile1 -> recentFile1.getDirectory().equals(recentFile.getDirectory())).map(file -> {
                    file.setTimeStamp(recentFile.getTimeStamp());
                    return file;
                }).findFirst().orElse(null);
    }
}
