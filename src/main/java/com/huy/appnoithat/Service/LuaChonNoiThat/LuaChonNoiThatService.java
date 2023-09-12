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
        String token = userSessionService.getToken();
        String response = webClientService.authorizedHttpGetJson(path, token);
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
        String token = userSessionService.getToken();
        String response = webClientService.authorizedHttpGetJson(path + "/" + id, token);
        try {
            PhongCachNoiThat phongCachNoiThat = objectMapper.readValue(response, PhongCachNoiThat.class);
            return phongCachNoiThat;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public PhongCachNoiThat findPhongCachNoiThatByName(String name) {
        String path = "/api/phongcach";
        String param = "?" + "name=" + Utils.encodeValue(name);
        String token = userSessionService.getToken();
        String response = webClientService.authorizedHttpGetJson(path + param, token);
        try {
            List<PhongCachNoiThat> phongCachNoiThatList = objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, PhongCachNoiThat.class));
            return phongCachNoiThatList.stream().filter(item -> item.getName().equals(name)).findFirst().orElse(null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public List<NoiThat> findNoiThatByPhongCachName(String name) {
        PhongCachNoiThat foundPhongCachNoiThat = findPhongCachNoiThatByName(name);
        if (foundPhongCachNoiThat == null) throw new NullPointerException("Phong cach not found");
        String path = "/api/noithat/searchByPhongCach";
        String token = userSessionService.getToken();
        String response = webClientService.authorizedHttpGetJson(path + "/" + foundPhongCachNoiThat.getId(), token);
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
        if (noiThatList == null) throw new NullPointerException("Not found");
        NoiThat foundNoiThat = noiThatList.stream().filter(nt -> nt.getName().equals(noiThat)).findFirst().orElse(null);
        if (foundNoiThat == null) throw new NullPointerException("Noi that not found");
        String path = "/api/hangmuc/searchByNoiThat";
        String token = userSessionService.getToken();
        String response = webClientService.authorizedHttpGetJson(path + "/" + foundNoiThat.getId(), token);
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
        if (hangMucList == null) throw new NullPointerException("Not found");
        HangMuc foundHangMuc = hangMucList.stream().filter(hm -> hm.getName().equals(hangMuc)).findFirst().orElse(null);
        if (foundHangMuc == null) throw new NullPointerException("Hang muc not found");

        String path = "/api/vatlieu/searchByHangMuc";
        String token = userSessionService.getToken();
        String response = webClientService.authorizedHttpGetJson(path + "/" + foundHangMuc.getId(), token);
        try {
            List<VatLieu> vatLieuList = objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, VatLieu.class));
            return vatLieuList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
