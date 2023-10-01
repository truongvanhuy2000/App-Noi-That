package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoiThatRestService {
    private static NoiThatRestService instance;
    final static Logger LOGGER = LogManager.getLogger(NoiThatRestService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService userSessionService;
    private static final String BASE_ENDPOINT = "/api/noithat";
    private static final String ID_TEMPLATE = "/%d";
    private static final String OWNER_TEMPLATE = "?owner=%s";
    private static final String PARENT_ID_TEMPLATE = "&parentId=%d";
    public static synchronized NoiThatRestService getInstance() {
        if (instance == null) {
            instance = new NoiThatRestService();
        }
        return instance;
    }
    private NoiThatRestService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
    @Deprecated
    public List<NoiThat> findAll() {
        return null;
    }
    @Deprecated
    public NoiThat findById(int id) {
        return null;
    }
    @Deprecated
    public NoiThat findUsingName(String name) {
        return null;
    }
    @Deprecated
    public void save(NoiThat noiThat) {
    }
    public List<NoiThat> searchByPhongCach(int id) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + "/searchByPhongCach" + ID_TEMPLATE + OWNER_TEMPLATE, id, userSessionService.getUsername());
        String response2 = this.webClientService.authorizedHttpGetJson(path, token);
        if (response2 == null) {
            return new ArrayList<>();
        }
        try {
            // 2. convert JSON array to List of objects
            return objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, NoiThat.class));
        } catch (IOException e) {
            LOGGER.error("Error when finding noi that");
            throw new RuntimeException(e);
        }
    }
    public void save(NoiThat noiThat, int parentID) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE + PARENT_ID_TEMPLATE, userSessionService.getUsername(), parentID);
        try {
            this.webClientService.authorizedHttpPostJson(path, objectMapper.writeValueAsString(noiThat), token);
        } catch (IOException e) {
            LOGGER.error("Error when adding new NoiThat");
            throw new RuntimeException(e);
        }
    }
    public void update(NoiThat noiThat) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE, userSessionService.getUsername());
        try {
            this.webClientService.authorizedHttpPutJson(path, objectMapper.writeValueAsString(noiThat), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing NoiThat");
            throw new RuntimeException(e);
        }
    }
    public void deleteById(int id) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + ID_TEMPLATE + OWNER_TEMPLATE, id, userSessionService.getUsername());
        this.webClientService.authorizedHttpDeleteJson(path, "", token);
    }
}