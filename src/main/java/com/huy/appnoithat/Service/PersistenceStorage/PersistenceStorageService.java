package com.huy.appnoithat.Service.PersistenceStorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.FileNoiThatExplorer.RecentFile;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.Session.PersistenceUserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PersistenceStorageService {
    final static Logger LOGGER = LogManager.getLogger(PersistenceStorageService.class);
    private static PersistenceStorageService instance;
    private ThongTinCongTy thongTinCongTy;
    private List<RecentFile> recentFileList;
    private PersistenceUserSession persistenceUserSession;
    private final ObjectMapper objectMapper;
    PersistenceStorageService() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
    public static synchronized PersistenceStorageService getInstance() {
        if (instance == null) {
            instance = new PersistenceStorageService();
        }
        return instance;
    }

    /**
     * Retrieves the company information from a file. If the information is already
     * loaded in memory, returns the existing object. If not, reads the information
     * from the specified file path and returns the parsed ThongTinCongTy object.
     *
     * @return ThongTinCongTy object containing company information.
     * @throws RuntimeException if there is an IOException while reading the file.
     */
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
    public void setThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        try {
            // Write company information to the specified file path
            objectMapper.writeValue(new File(Config.USER.COMPANY_INFO_DIRECTORY), thongTinCongTy);
        } catch (IOException e) {
            LOGGER.error("Failed to write company info" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public List<RecentFile> getRecentFileList() {
        try {
            this.recentFileList = objectMapper.readValue(new File(Config.FILE_EXPORT.RECENT_NT_FILE_DIRECTORY),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, RecentFile.class));
            return recentFileList;
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
    public void addRecentFile(RecentFile recentFile) {
        try {
            // Read recent files from the specified file path
            this.recentFileList.add(recentFile);
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
    public void setUserSession(PersistenceUserSession persistenceUserSession) {
        this.persistenceUserSession = persistenceUserSession;
        try {
            objectMapper.writeValue(new File(Config.USER.SESSION_DIRECTORY), persistenceUserSession);
        } catch (IOException e) {
            LOGGER.error("Failed to write user session" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
