package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class VatLieuRestService {
    private static VatLieuRestService instance;
    final static Logger LOGGER = LogManager.getLogger(VatLieuRestService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService userSessionService;
    public static synchronized VatLieuRestService getInstance() {
        if (instance == null) {
            instance = new VatLieuRestService();
        }
        return instance;
    }
    private VatLieuRestService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
    public List<VatLieu> searchByHangMuc(int id) {
        String token = this.userSessionService.getToken();
        String response = this.webClientService.authorizedHttpGetJson("/api/vatlieu/searchByHangMuc/" + id, token);
        if (response == null) {
            return null;
        }
        try {
            // 2. convert JSON array to List of objects
            return objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, VatLieu.class));
        } catch (IOException e) {
            LOGGER.error("Error when finding vat lieu");
            throw new RuntimeException(e);
        }
    }

    public void save(VatLieu vatLieu, int parentID) {
        String token = this.userSessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/vatlieu?parentId=" + parentID, objectMapper.writeValueAsString(vatLieu), token);
        } catch (IOException e) {
            LOGGER.error("Error when adding new VatLieu");
            throw new RuntimeException(e);
        }
    }

    public void update(VatLieu vatLieu) {
        String token = this.userSessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/vatlieu", objectMapper.writeValueAsString(vatLieu), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing VatLieu");
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        String token = this.userSessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson("/api/vatlieu/" + id, "", token);
    }
}
