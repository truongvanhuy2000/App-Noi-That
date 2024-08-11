package com.huy.appnoithat.Service.FileNoiThatExplorer;

import com.huy.appnoithat.DataModel.RecentFile;
import com.huy.appnoithat.Service.PersistenceStorage.StorageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LocalFileNoiThatExplorerService implements FileNoiThatExplorerService {
    final static Logger LOGGER = LogManager.getLogger(LocalFileNoiThatExplorerService.class);
    private ObservableList<RecentFile> recentFileObservableList = FXCollections.observableArrayList();
    private final StorageService persistenceStorageService;

    public LocalFileNoiThatExplorerService(StorageService persistenceStorageService) {
        this.persistenceStorageService = persistenceStorageService;
    }

    /**
     * Gets the observable list of recent files. If the list is not initialized, it reads the data from the file.
     *
     * @return ObservableList of RecentFile objects.
     * @throws RuntimeException if there is an IOException while reading the file.
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
    public void removeRecentFile(RecentFile recentFile) {
        // Retrieve the recent file list if it's not already loaded
        if (recentFileObservableList == null) {
            recentFileObservableList = getRecentFile();
        }
        // Remove the recent file from the list and save the updated list to the file system
        recentFileObservableList.remove(recentFile);
        removePhysicalFile(recentFile);
        saveRecentFile();
    }

    private void removePhysicalFile(RecentFile recentFile) {
        if (Files.exists(Path.of(recentFile.getDirectory()))) {
            try {
                Files.delete(Path.of(recentFile.getDirectory()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
