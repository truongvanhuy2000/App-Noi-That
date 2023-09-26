package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ThongSoRestService {
    private static ThongSoRestService instance;
    final static Logger LOGGER = LogManager.getLogger(VatLieuRestService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService userSessionService;
    public static synchronized ThongSoRestService getInstance() {
        if (instance == null) {
            instance = new ThongSoRestService();
        }
        return instance;
    }
    private ThongSoRestService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
    public List<ThongSo> searchByVatLieu(int id) {
        String token = this.userSessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/thongso/searchByVatlieu/" + id, token);
        if (response2 == null) {
            return null;
        }
        try {
            // 2. convert JSON array to List of objects
            return objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, ThongSo.class));
        } catch (IOException e) {
            LOGGER.error("Error when finding thong so");
            throw new RuntimeException(e);
        }
    }

    public void update(ThongSo thongSo) {
        String token = this.userSessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/thongso", objectMapper.writeValueAsString(thongSo), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing ThongSo");
            throw new RuntimeException(e);
        }
    }
}
