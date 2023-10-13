package com.huy.appnoithat.Service.PersistenceStorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.FileNoiThatExplorer.RecentFile;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Service.RestService.HangMucRestService;
import com.huy.appnoithat.Service.RestService.NoiThatRestService;
import com.huy.appnoithat.Service.SessionService.UserSession;
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
    private UserSession userSession;
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
    public ThongTinCongTy getThongTinCongTy() {
        if (thongTinCongTy != null) {
            return thongTinCongTy;
        }
        try {
            return objectMapper.readValue(new File(Config.USER.COMPANY_INFO_DIRECTORY), ThongTinCongTy.class);
        } catch (IOException e) {
            LOGGER.error("Failed to read company info" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void setThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        this.thongTinCongTy = thongTinCongTy;
        try {
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
    public void addRecentFile(RecentFile recentFile) {
        try {
            this.recentFileList.add(recentFile);
            objectMapper.writeValue(new File(Config.FILE_EXPORT.RECENT_NT_FILE_DIRECTORY), recentFileList);
        } catch (IOException e) {
            LOGGER.error("Failed to write recent file" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public UserSession getUserSession() {
        try {
            return objectMapper.readValue(new File(Config.USER.SESSION_DIRECTORY), UserSession.class);
        } catch (IOException e) {
            LOGGER.error("Failed to read user session" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
        try {
            objectMapper.writeValue(new File(Config.USER.SESSION_DIRECTORY), userSession);
        } catch (IOException e) {
            LOGGER.error("Failed to write user session" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
