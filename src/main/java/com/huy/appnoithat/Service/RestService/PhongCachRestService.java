package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhongCachRestService {
    private static PhongCachRestService instance;
    final static Logger LOGGER = LogManager.getLogger(PhongCachRestService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService userSessionService;
    public static synchronized PhongCachRestService getInstance() {
        if (instance == null) {
            instance = new PhongCachRestService();
        }
        return instance;
    }
    private PhongCachRestService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
    public List<PhongCachNoiThat> findAll() {
        String path = "/api/phongcach";
        String response = webClientService.authorizedHttpGetJson(path, userSessionService.getToken());
        if (response == null) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(response,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PhongCachNoiThat.class));
        } catch (JsonProcessingException e) {
            LOGGER.error("Can't parse response from server");
            throw new RuntimeException(e);
        }
    }
    public PhongCachNoiThat findById(int id) {
        String path = "/api/phongcach";
        String response = webClientService.authorizedHttpGetJson(path + "/" + id, userSessionService.getToken());
        if (response == null) {
            return null;
        }
        try {
            return objectMapper.readValue(response, PhongCachNoiThat.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Can't parse response from server");
            throw new RuntimeException(e);
        }
    }
    public PhongCachNoiThat findUsingName(String name) {
        String path = "/api/phongcach/search";
        String param = "?" + "name=" + Utils.encodeValue(name);
        String response = webClientService.authorizedHttpGetJson(path + param, userSessionService.getToken());
        if (response == null) {
            return null;
        }
        try {
            return objectMapper.readValue(response, PhongCachNoiThat.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Can't parse response from server");
            throw new RuntimeException(e);
        }
    }
    public void save(PhongCachNoiThat phongCachNoiThat) {
        String token = this.userSessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/phongcach", objectMapper.writeValueAsString(phongCachNoiThat), token);
        } catch (IOException e) {
            LOGGER.error("Error when adding new PhongCach");
            throw new RuntimeException(e);
        }
    }
    public void update(PhongCachNoiThat phongCachNoiThat) {
        String token = this.userSessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/phongcach", objectMapper.writeValueAsString(phongCachNoiThat), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing PhongCach");
            throw new RuntimeException(e);
        }
    }
    public void deleteById(int id) {
        String token = this.userSessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson("/api/phongcach/" + id, "", token);
    }

}
