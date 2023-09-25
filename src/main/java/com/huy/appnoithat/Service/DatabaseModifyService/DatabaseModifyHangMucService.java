package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyHangMucService {
    final static Logger LOGGER = LogManager.getLogger(DatabaseModifyHangMucService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private String token;
    private final UserSessionService sessionService;

    public DatabaseModifyHangMucService() {
        sessionService = new UserSessionService();
        webClientService = new WebClientServiceImpl();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    public List<HangMuc> findHangMucByID(int id) {
        List<HangMuc> tempHangMucList = new ArrayList<>();
        token = this.sessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/hangmuc/searchByNoiThat/" + id, token);
        try {
            // 2. convert JSON array to List of objects
            List<HangMuc> hangMucList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, HangMuc.class));
            for (HangMuc hangMuc : hangMucList) {
                HangMuc hangMuc1 = new HangMuc();
                hangMuc1.setId(hangMuc.getId());
                hangMuc1.setName(hangMuc.getName());
                hangMuc1.setVatLieuList(hangMuc.getVatLieuList());
                tempHangMucList.add(hangMuc1);
            }
        } catch (IOException e) {
            LOGGER.error("Error when finding hang muc");
            throw new RuntimeException(e);
        }
        return tempHangMucList;
    }

    public void addNewHangMuc(HangMuc hangMuc, int parentID) {
        token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/hangmuc?parentId=" + parentID, objectMapper.writeValueAsString(hangMuc), token);
        } catch (IOException e) {
            LOGGER.error("Error when adding new HangMuc");
            throw new RuntimeException(e);
        }
    }

    public void EditHangMuc(HangMuc hangMuc) {
        token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/hangmuc", objectMapper.writeValueAsString(hangMuc), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing HangMuc");
            throw new RuntimeException(e);
        }
    }

    public void deleteHangMuc(int id) {
        token = this.sessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson("/api/hangmuc/" + id, "", token);
    }
}
