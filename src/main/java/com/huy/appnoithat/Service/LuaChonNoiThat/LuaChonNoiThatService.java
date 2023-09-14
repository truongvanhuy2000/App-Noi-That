package com.huy.appnoithat.Service.LuaChonNoiThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import com.huy.appnoithat.Shared.Utils;

import java.awt.desktop.PreferencesEvent;
import java.util.ArrayList;
import java.util.List;

public class LuaChonNoiThatService {
    private final WebClientService webClientService;
    private final UserSessionService userSessionService;
    private final ObjectMapper objectMapper;

    // Fake the data
    public LuaChonNoiThatService() {
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    public List<PhongCachNoiThat> findAllPhongCachNoiThat() {
        String path = "/api/phongcach";
        String response = webClientService.authorizedHttpGetJson(path, userSessionService.getToken());
        if (response == null) {
            return new ArrayList<>();
        }
        try {
            List<PhongCachNoiThat> phongCachNoiThatList = objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, PhongCachNoiThat.class));
            return phongCachNoiThatList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public PhongCachNoiThat findPhongCachNoiThatById(int id) {
        String path = "/api/phongcach";
        String response = webClientService.authorizedHttpGetJson(path + "/" + id, userSessionService.getToken());
        try {
            PhongCachNoiThat phongCachNoiThat = objectMapper.readValue(response, PhongCachNoiThat.class);
            return phongCachNoiThat;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public PhongCachNoiThat findPhongCachNoiThatByName(String name) {
        String path = "/api/phongcach/search";
        String param = "?" + "name=" + Utils.encodeValue(name);
        String response = webClientService.authorizedHttpGetJson(path + param, userSessionService.getToken());
        try {
            PhongCachNoiThat phongCachNoiThat = objectMapper.readValue(response, PhongCachNoiThat.class);
            return phongCachNoiThat;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public List<NoiThat> findNoiThatByPhongCachName(String name) {
        PhongCachNoiThat foundPhongCachNoiThat = findPhongCachNoiThatByName(name);
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
            throw new RuntimeException(e);
        }
    }

    public List<HangMuc> findHangMucListByPhongCachAndNoiThat(String phongCach, String noiThat){
        List<NoiThat> noiThatList = findNoiThatByPhongCachName(phongCach);
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
            throw new RuntimeException(e);
        }
    }
    public List<VatLieu> findVatLieuListByParentsName(String phongCach, String noiThat, String hangMuc){
        List<HangMuc> hangMucList = findHangMucListByPhongCachAndNoiThat(phongCach, noiThat);
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
}
