package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.Service.WebClient.WebClientService;

public class BangNoiThatRestService {
    private static final String BASE_ENDPOINT = "/api/bangnoithat";
    private final WebClientService webClientService;
    private static BangNoiThatRestService instance;
    public static synchronized BangNoiThatRestService getInstance() {
        if (instance == null) {
            instance = new BangNoiThatRestService();
        }
        return instance;
    }
    private BangNoiThatRestService() {
        webClientService = new WebClientService();
    }
    public void sampleAll() {
        String path = String.format(BASE_ENDPOINT + "/sampleAll");
        this.webClientService.authorizedHttpGetJson(path);
    }
}
