package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyPhongCachService {
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService sessionService;

    public DatabaseModifyPhongCachService(){
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        sessionService = new UserSessionService();
    }

    public List<PhongCachNoiThat> findAllPhongCach(){
        List<PhongCachNoiThat> tempPhongCachList = new ArrayList<>();
        String token = this.sessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/phongcach", token);
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
            throw new RuntimeException("Error when convert JSON to List<PhongCachNoiThat>");
        }
        return tempPhongCachList;
    }

    public void addNewPhongCach(PhongCachNoiThat phongCachNoiThat){
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/phongcach",  objectMapper.writeValueAsString(phongCachNoiThat),token);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("");
        }
    }

    public void EditPhongCach(PhongCachNoiThat phongCachNoiThat){
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/phongcach",  objectMapper.writeValueAsString(phongCachNoiThat),token);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("");
        }
    }

    public void deletePhongCach(int id){
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson("/api/phongcach/"+id,  "",token);
    }
//    public PhongCachNoiThat findByID(int id){
//        token = this.sessionService.getToken();
//        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
//        String response2 = this.webClientService.authorizedHttpGetJson("/api/noithat/searchByPhongCach/"+id, token);
//        objectMapper = JsonMapper.builder()
//                .addModule(new JavaTimeModule())
//                .build();
//        PhongCachNoiThat phongCachNoiThat = new PhongCachNoiThat();
//        try {
//            phongCachNoiThat = objectMapper.readValue(response2,PhongCachNoiThat.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return phongCachNoiThat;
//    }

}
