package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

public class BangNoiThatRestService {
    private static final String BASE_ENDPOINT = "/api/bangnoithat";
    private final UserSessionService userSessionService;
    private final WebClientService webClientService;
    private static BangNoiThatRestService instance;
    public static synchronized BangNoiThatRestService getInstance() {
        if (instance == null) {
            instance = new BangNoiThatRestService();
        }
        return instance;
    }
    private BangNoiThatRestService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
    }
    public void sampleAll() {
        String path = String.format(BASE_ENDPOINT + "/sampleAll");
        String token = this.userSessionService.getToken();
        this.webClientService.authorizedHttpGetJson(path, token);
    }
}
