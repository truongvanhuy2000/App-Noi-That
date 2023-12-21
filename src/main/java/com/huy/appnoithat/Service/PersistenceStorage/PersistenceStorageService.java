package com.huy.appnoithat.Service.PersistenceStorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.DataModel.RecentFile;
import com.huy.appnoithat.DataModel.Session.PersistenceUserSession;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PersistenceStorageService implements StorageService {
    final static Logger LOGGER = LogManager.getLogger(PersistenceStorageService.class);
    private final ObjectMapper objectMapper;
    public PersistenceStorageService() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
    /**
     * Retrieves the company information from a file. If the information is already
     * loaded in memory, returns the existing object. If not, reads the information
     * from the specified file path and returns the parsed ThongTinCongTy object.
     *
     * @return ThongTinCongTy object containing company information.
     * @throws RuntimeException if there is an IOException while reading the file.
     */
    @Override
    public ThongTinCongTy getThongTinCongTy() {
        try {
            // Read company information from the specified file path
            return objectMapper.readValue(new File(Config.USER.COMPANY_INFO_DIRECTORY), ThongTinCongTy.class);
        } catch (IOException e) {
            LOGGER.error("Failed to read company info" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the company information and writes it to the specified file path.
     *
     * @param thongTinCongTy ThongTinCongTy object containing company information to be set.
     * @throws RuntimeException if there is an IOException while writing the file.
     */
    @Override
    public void saveThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        try {
            // Write company information to the specified file path
            objectMapper.writeValue(new File(Config.USER.COMPANY_INFO_DIRECTORY), thongTinCongTy);
        } catch (IOException e) {
            LOGGER.error("Failed to write company info" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<RecentFile> getRecentFileList() {
        try {
            return objectMapper.readValue(new File(Config.FILE_EXPORT.RECENT_NT_FILE_DIRECTORY),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, RecentFile.class));
        } catch (IOException e) {
            LOGGER.error("Failed to read recent file" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the list of recent files from the specified file path.
     *
     * @return List of RecentFile objects representing the recent files.
     * @throws RuntimeException if there is an IOException while reading the file.
     */
    @Override
    public void saveRecentFile(List<RecentFile> recentFileList) {
        try {
            // Read recent files from the specified file path
            objectMapper.writeValue(new File(Config.FILE_EXPORT.RECENT_NT_FILE_DIRECTORY), recentFileList);
        } catch (IOException e) {
            LOGGER.error("Failed to write recent file" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /**
     * Retrieves the user session from the specified file path.
     *
     * @return UserSession object representing the user session.
     * @throws RuntimeException if there is an IOException while reading the file.
     */
    @Override
    public PersistenceUserSession getUserSession() {
        try {
            // Read user session from the specified file path
            return objectMapper.readValue(new File(Config.USER.SESSION_DIRECTORY), PersistenceUserSession.class);
        } catch (IOException e) {
            LOGGER.error("Failed to read user session" + e.getMessage());
            return null;
        }
    }

    /**
     * Sets the user session and writes it to the specified file path.
     *
     * @param persistenceUserSession UserSession object representing the user session to be set.
     * @throws RuntimeException if there is an IOException while writing the file.
     */
    @Override
    public void saveUserSession(PersistenceUserSession persistenceUserSession) {
        try {
            objectMapper.writeValue(new File(Config.USER.SESSION_DIRECTORY), persistenceUserSession);
        } catch (IOException e) {
            LOGGER.error("Failed to write user session" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveNoteArea(String noteArea) {
        try {
            objectMapper.writeValue(new File(Config.USER.NOTE_AREA_DIRECTORY), noteArea);
        } catch (IOException e) {
            LOGGER.error("Failed to write note area" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getNoteArea() {
        try {
            return objectMapper.readValue(new File(Config.USER.NOTE_AREA_DIRECTORY), String.class);
        } catch (IOException e) {
            LOGGER.error("Failed to read note area" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
