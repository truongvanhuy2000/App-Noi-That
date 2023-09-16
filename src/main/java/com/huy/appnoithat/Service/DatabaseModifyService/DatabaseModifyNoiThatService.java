package com.huy.appnoithat.Service.DatabaseModifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModifyNoiThatService {

    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService;

    public DatabaseModifyNoiThatService(){
        sessionService = new UserSessionService();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    public List<NoiThat> findNoiThatByID(int id){
        List<NoiThat> tempNoiThatList = new ArrayList<>();
        token = this.sessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/noithat/searchByPhongCach/"+id, token);
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
        token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/noithat?parentId="+parentID,  objectMapper.writeValueAsString(noiThat),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EditNoiThat(NoiThat noiThat){
        token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/noithat",  objectMapper.writeValueAsString(noiThat),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNoiThat(int id){
        token = this.sessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson("/api/noithat/"+id,  "",token);
    }
}
