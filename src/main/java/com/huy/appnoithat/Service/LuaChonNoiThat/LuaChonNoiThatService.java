package com.huy.appnoithat.Service.LuaChonNoiThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Enums.FileType;
import com.huy.appnoithat.Service.RestService.PhongCachRestService;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import com.huy.appnoithat.Service.FileExport.FileExportService;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LuaChonNoiThatService {
    final static Logger LOGGER = LogManager.getLogger(LuaChonNoiThatService.class);
    private final WebClientService webClientService;
    private final UserSessionService userSessionService;
    private final ObjectMapper objectMapper;
    private final FileExportService fileExportService;
    private final PhongCachRestService phongCachRestService;

    public LuaChonNoiThatService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        fileExportService = new FileExportService();
        phongCachRestService = PhongCachRestService.getInstance();
    }

    public List<PhongCachNoiThat> findAllPhongCachNoiThat() {
        return phongCachRestService.findAll();
    }

    public PhongCachNoiThat findPhongCachById(int id) {
        return phongCachRestService.findById(id);
    }

    public PhongCachNoiThat findPhongCachNoiThatByName(String name) {
        return phongCachRestService.findUsingName(name);
    }

    public List<NoiThat> findNoiThatListBy(String phongCach) {
        PhongCachNoiThat foundPhongCachNoiThat = findPhongCachNoiThatByName(phongCach);
        if (foundPhongCachNoiThat == null) {
            return new ArrayList<>();
        }
        String path = "/api/noithat/searchByPhongCach";
        String response = webClientService.authorizedHttpGetJson(path + "/" + foundPhongCachNoiThat.getId(), userSessionService.getToken());
        if (response == null) {
            return new ArrayList<>();
        }
        try {
            List<NoiThat> noiThatList = objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, NoiThat.class));
            return noiThatList;
        } catch (JsonProcessingException e) {
            LOGGER.error("Can't parse response from server");
            throw new RuntimeException(e);
        }
    }

    public List<HangMuc> findHangMucListBy(String phongCach, String noiThat) {
        List<NoiThat> noiThatList = findNoiThatListBy(phongCach);
        if (noiThatList == null) {
            return new ArrayList<>();
        }
        NoiThat foundNoiThat = noiThatList.stream().filter(nt -> nt.getName().equals(noiThat)).findFirst().orElse(null);
        if (foundNoiThat == null) {
            return new ArrayList<>();
        }
        String path = "/api/hangmuc/searchByNoiThat";
        String response = webClientService.authorizedHttpGetJson(path + "/" + foundNoiThat.getId(), userSessionService.getToken());
        if (response == null) {
            return new ArrayList<>();
        }
        try {
            List<HangMuc> hangMucList = objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, HangMuc.class));
            return hangMucList;
        } catch (JsonProcessingException e) {
            LOGGER.error("Can't parse response from server");
            throw new RuntimeException(e);
        }
    }

    public List<VatLieu> findVatLieuListBy(String phongCach, String noiThat, String hangMuc) {
        List<HangMuc> hangMucList = findHangMucListBy(phongCach, noiThat);
        if (hangMucList == null) {
            return new ArrayList<>();
        }
        HangMuc foundHangMuc = hangMucList.stream().filter(hm -> hm.getName().equals(hangMuc)).findFirst().orElse(null);
        if (foundHangMuc == null) {
            return new ArrayList<>();
        }
        String path = "/api/vatlieu/searchByHangMuc";
        String response = webClientService.authorizedHttpGetJson(path + "/" + foundHangMuc.getId(), userSessionService.getToken());
        if (response == null) {
            return new ArrayList<>();
        }
        try {
            List<VatLieu> vatLieuList = objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, VatLieu.class));
            return vatLieuList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exportFile(File selectedFile, FileType fileType, DataPackage dataPackage) {
        ExportFile exportFile = fileExportService.getExportService(selectedFile, fileType);
        exportFile.setUpDataForExport(dataPackage);
        try {
            exportFile.export(selectedFile);
            return true;
        } catch (IOException e) {
            LOGGER.error("Some thing is wrong with the export operation", e);
            return false;
        }
    }

    public DataPackage importFile(File selectedFile) {
        ExportFile exportFile = fileExportService.getExportService(selectedFile, FileType.NT);
        return exportFile.importData(selectedFile);
    }

}
