package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyNoiThatService {
    private List<NoiThat> tempNoiThatList = new ArrayList<>();

    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService = new UserSessionService();

    public DatabaseModifyNoiThatService(){}

    public List<NoiThat> findNoiThatByID(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/noithat/searchByPhongCach/"+id, token);
        objectMapper = new ObjectMapper();

        try {
            // 2. convert JSON array to List of objects
            List<NoiThat> noiThatList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, NoiThat.class));
            for (NoiThat noiThat: noiThatList) {
                NoiThat noiThat1 = new NoiThat();
                noiThat1.setId(noiThat.getId());
                noiThat1.setName(noiThat.getName());
                noiThat1.setHangMucList(noiThat.getHangMucList());
                tempNoiThatList.add(noiThat1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempNoiThatList;
    }

    public void addNewNoiThat(NoiThat noiThat,int parentID){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        try {
            this.webClientService.authorizedHttpPostJson("/api/noithat?parentId="+parentID,  objectMapper.writeValueAsString(noiThat),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EditNoiThat(NoiThat noiThat){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        try {
            this.webClientService.authorizedHttpPutJson("/api/noithat",  objectMapper.writeValueAsString(noiThat),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNoiThat(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        this.webClientService.authorizedHttpDeleteJson("/api/noithat/"+id,  "",token);
    }
}
