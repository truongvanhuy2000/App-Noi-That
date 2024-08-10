package com.huy.appnoithat.Service.PersistenceStorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.configuration.Config;
import com.huy.appnoithat.DataModel.RecentFile;
import com.huy.appnoithat.DataModel.Session.PersistenceUserSession;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Service.RestService.LapBaoGiaRestService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class PersistenceStorageService implements StorageService {
    final static Logger LOGGER = LogManager.getLogger(PersistenceStorageService.class);
    private final ObjectMapper objectMapper;
    private final LapBaoGiaRestService lapBaoGiaRestService;
    private final CachedData cachedData;

    public PersistenceStorageService(ObjectMapper objectMapper, LapBaoGiaRestService lapBaoGiaRestService, CachedData cachedData) {
        this.objectMapper = objectMapper;
        this.lapBaoGiaRestService = lapBaoGiaRestService;
        this.cachedData = cachedData;
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
        if (cachedData.getThongTinCongTy() != null) {
            return cachedData.getThongTinCongTy();
        }
        try {
            ThongTinCongTy thongTinCongTy = objectMapper.readValue(
                    new File(Config.USER.COMPANY_INFO_DIRECTORY), ThongTinCongTy.class);
            if (thongTinCongTy == null || thongTinCongTy.getCreatedDate() == null ||
                    lapBaoGiaRestService.checkInfoModification(thongTinCongTy.getCreatedDate())) {
                thongTinCongTy = lapBaoGiaRestService.getThongTinCongTy();
                if (thongTinCongTy != null) {
                    saveThongTinCongTy(thongTinCongTy);
                }
            }
            cachedData.setThongTinCongTy(thongTinCongTy);
            // Read company information from the specified file path
            return thongTinCongTy;
        } catch (IOException e) {
            LOGGER.error("Failed to read company info{}", e.getMessage());
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
            objectMapper.writeValue(new File(Config.USER.COMPANY_INFO_DIRECTORY), thongTinCongTy);
            thongTinCongTy.setCreatedDate(new Date());
            lapBaoGiaRestService.saveThongTinCongTy(thongTinCongTy);
        } catch (IOException e) {
            LOGGER.error("Failed to write company info{}", e.getMessage());
            throw new RuntimeException(e);
        }
        cachedData.setThongTinCongTy(thongTinCongTy);
    }
    @Override
    public List<RecentFile> getRecentFileList() {
        try {
            return objectMapper.readValue(new File(Config.FILE_EXPORT.RECENT_NT_FILE_DIRECTORY),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, RecentFile.class));
        } catch (IOException e) {
            LOGGER.error("Failed to read recent file{}", e.getMessage());
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
            LOGGER.error("Failed to write recent file{}", e.getMessage());
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
            LOGGER.error("Failed to read user session{}", e.getMessage());
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
            LOGGER.error("Failed to write user session{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveNoteArea(String noteArea) {
        try {
            objectMapper.writeValue(new File(Config.USER.NOTE_AREA_DIRECTORY), noteArea);
            lapBaoGiaRestService.saveNote(noteArea);
        } catch (IOException e) {
            LOGGER.error("Failed to write note area{}", e.getMessage());
            throw new RuntimeException(e);
        }
        cachedData.setNoteArea(noteArea);
    }
    @Override
    public String getNoteArea() {
        if (StringUtils.isNotEmpty(cachedData.getNoteArea())) {
            return cachedData.getNoteArea();
        }
        String note = "";
        try {
            note = objectMapper.readValue(new File(Config.USER.NOTE_AREA_DIRECTORY), String.class);
        } catch (IOException e) {
            LOGGER.error("Failed to read note area{}", e.getMessage());
        }
        if (StringUtils.isEmpty(note)) {
            // Remove the double quote because I don't even know how they appear there
            note = removeDoubleQuote(lapBaoGiaRestService.getNote());
        }
        cachedData.setNoteArea(note);
        return note;

    }

    private String removeDoubleQuote(String string) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        if (string.startsWith("\"") && string.endsWith("\"")) {
            string = string.substring(1, string.length() - 1);
        }
        return string;
    }

    @Data
    public static class CachedData {
        private ThongTinCongTy thongTinCongTy;
        private String noteArea;
    }
}
