package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyVatlieuService {
    private List<VatLieu> tempVatLieuList = new ArrayList<>();

    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService = new UserSessionService();

    public DatabaseModifyVatlieuService(){}

    public List<VatLieu> findVatLieuByID(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/vatlieu/searchByHangMuc/"+id, token);
        objectMapper = new ObjectMapper();

        try {
            // 2. convert JSON array to List of objects
            List<VatLieu> vatLieuList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, VatLieu.class));
            for (VatLieu vatLieu: vatLieuList) {
                VatLieu vatLieu1 = new VatLieu();
                vatLieu1.setId(vatLieu.getId());
                vatLieu1.setName(vatLieu.getName());
                vatLieu1.setThongSo(vatLieu.getThongSo());
                tempVatLieuList.add(vatLieu1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempVatLieuList;
    }

    public void addNewVatLieu(VatLieu vatLieu,int parentID){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        try {
            this.webClientService.authorizedHttpPostJson("/api/vatlieu?parentId="+parentID,  objectMapper.writeValueAsString(vatLieu),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EditVatLieu(VatLieu vatLieu){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        try {
            this.webClientService.authorizedHttpPutJson("/api/vatlieu",  objectMapper.writeValueAsString(vatLieu),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteVatLieu(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        this.webClientService.authorizedHttpDeleteJson("/api/vatlieu/"+id,  "",token);
    }
}
