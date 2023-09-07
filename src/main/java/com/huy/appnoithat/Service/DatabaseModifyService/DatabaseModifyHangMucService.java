package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.NoiThat;
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

    public List<HangMuc> findHangMucByID(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/hangmuc/searchByNoiThat/"+id, token);
        objectMapper = new ObjectMapper();

        try {
            // 2. convert JSON array to List of objects
            List<HangMuc> hangMucList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, HangMuc.class));
            for (HangMuc hangMuc: hangMucList) {
                HangMuc hangMuc1 = new HangMuc();
                hangMuc1.setId(hangMuc.getId());
                hangMuc1.setName(hangMuc.getName());
                hangMuc1.setVatLieuList(hangMuc.getVatLieuList());
                tempHangMucList.add(hangMuc1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempHangMucList;
    }

    public void addNewHangMuc(HangMuc hangMuc,int parentID){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        try {
            this.webClientService.authorizedHttpPostJson("/api/hangmuc?parentId="+parentID,  objectMapper.writeValueAsString(hangMuc),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EditHangMuc(HangMuc hangMuc){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        try {
            this.webClientService.authorizedHttpPutJson("/api/hangmuc",  objectMapper.writeValueAsString(hangMuc),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteHangMuc(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        this.webClientService.authorizedHttpDeleteJson("/api/hangmuc/"+id,  "",token);
    }
}
