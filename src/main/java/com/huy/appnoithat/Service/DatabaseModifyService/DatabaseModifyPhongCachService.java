package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyPhongCachService {
    private final List<PhongCachNoiThat> tempPhongCachList = new ArrayList<>();

    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService = new UserSessionService();

    public DatabaseModifyPhongCachService(){}

    public List<PhongCachNoiThat> findAllPhongCach(){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/phongcach", token);
        objectMapper = new ObjectMapper();

        try {
            // 2. convert JSON array to List of objects
            List<PhongCachNoiThat> PhongCachNoiThatList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, PhongCachNoiThat.class));
            for (PhongCachNoiThat phongcach: PhongCachNoiThatList) {
                PhongCachNoiThat phongCachNoiThat = new PhongCachNoiThat();
                phongCachNoiThat.setId(phongcach.getId());
                phongCachNoiThat.setName(phongcach.getName());
                phongCachNoiThat.setNoiThatList(phongcach.getNoiThatList());
                tempPhongCachList.add(phongCachNoiThat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempPhongCachList;
    }
}
