package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyVatlieuService {

    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService;

    public DatabaseModifyVatlieuService() {
        sessionService = new UserSessionService();
        webClientService = new WebClientServiceImpl();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    public List<VatLieu> findVatLieuByID(int id) {
        List<VatLieu> tempVatLieuList = new ArrayList<>();
        token = this.sessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/vatlieu/searchByHangMuc/" + id, token);
        try {
            // 2. convert JSON array to List of objects
            List<VatLieu> vatLieuList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, VatLieu.class));
            for (VatLieu vatLieu : vatLieuList) {
                VatLieu vatLieu1 = new VatLieu();
                vatLieu1.setId(vatLieu.getId());
                vatLieu1.setName(vatLieu.getName());
                vatLieu1.setThongSo(vatLieu.getThongSo());
                tempVatLieuList.add(vatLieu1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempVatLieuList;
    }

    public void addNewVatLieu(VatLieu vatLieu, int parentID) {
        token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/vatlieu?parentId=" + parentID, objectMapper.writeValueAsString(vatLieu), token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void EditVatLieu(VatLieu vatLieu) {
        token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/vatlieu", objectMapper.writeValueAsString(vatLieu), token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteVatLieu(int id) {
        token = this.sessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson("/api/vatlieu/" + id, "", token);
    }
}
