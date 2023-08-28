package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyHangMucService {
    private final List<HangMuc> tempHangMucList = new ArrayList<>();

    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService = new UserSessionService();

    public DatabaseModifyHangMucService(){}

    public List<HangMuc> findAllHangMuc(){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/hangmuc", token);
        objectMapper = new ObjectMapper();

        try {
            // 2. convert JSON array to List of objects
            List<HangMuc> hangMucList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, HangMuc.class));
            for (HangMuc hm: hangMucList) {
                HangMuc hangMuc = new HangMuc();
                hangMuc.setId(hm.getId());
                hangMuc.setName(hm.getName());
                hangMuc.setVatLieuList(hm.getVatLieuList());
                tempHangMucList.add(hangMuc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempHangMucList;
    }
}
