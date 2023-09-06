package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyThongSoService {
    private List<ThongSo> tempThongSoList = new ArrayList<>();

    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService = new UserSessionService();

    public DatabaseModifyThongSoService(){}

    public List<ThongSo> findThongSoByID(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/thongso/searchByVatlieu/"+id, token);
        objectMapper = new ObjectMapper();

        try {
            // 2. convert JSON array to List of objects
            List<ThongSo> thongSoList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, ThongSo.class));
            for (ThongSo thongSo: thongSoList) {
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
            e.printStackTrace();
        }
        return tempThongSoList;
    }


    public void EditThongSo(ThongSo thongSo){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        try {
            this.webClientService.authorizedHttpPutJson("/api/thongso",  objectMapper.writeValueAsString(thongSo),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
