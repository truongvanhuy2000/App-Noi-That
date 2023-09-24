package com.huy.appnoithat.Service.DatabaseModifyService;

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
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyThongSoService {
    final static Logger LOGGER = LogManager.getLogger(DatabaseModifyThongSoService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private String token;
    private final UserSessionService sessionService;

    public DatabaseModifyThongSoService() {
        webClientService = new WebClientServiceImpl();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        sessionService = new UserSessionService();
    }

    public List<ThongSo> findThongSoByID(int id) {
        List<ThongSo> tempThongSoList = new ArrayList<>();
        token = this.sessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/thongso/searchByVatlieu/" + id, token);
        try {
            // 2. convert JSON array to List of objects
            List<ThongSo> thongSoList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, ThongSo.class));
            for (ThongSo thongSo : thongSoList) {
                ThongSo thongSo1 = new ThongSo();
                thongSo1.setId(thongSo.getId());
                thongSo1.setCao(thongSo.getCao());
                thongSo1.setDai(thongSo.getDai());
                thongSo1.setRong(thongSo.getRong());
                thongSo1.setDon_vi(thongSo.getDon_vi());
                thongSo1.setDon_gia(thongSo.getDon_gia());
                tempThongSoList.add(thongSo1);
            }
        } catch (IOException e) {
            LOGGER.error("Error when finding thong so");
            throw new RuntimeException(e);
        }
        return tempThongSoList;
    }

    public void EditThongSo(ThongSo thongSo) {
        token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/thongso", objectMapper.writeValueAsString(thongSo), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing ThongSo");
            throw new RuntimeException(e);
        }
    }

}
