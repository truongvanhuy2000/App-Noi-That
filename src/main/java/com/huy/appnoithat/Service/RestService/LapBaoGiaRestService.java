package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.Service.WebClient.WebClientService;

public class LapBaoGiaRestService {
    private static final String BASE_ENDPOINT = "/api/lapBaoGiaInfo";
    private final WebClientService webClientService;
    private static LapBaoGiaRestService instance;
    public static synchronized LapBaoGiaRestService getInstance() {
        if (instance == null) {
            instance = new LapBaoGiaRestService();
        }
        return instance;
    }
    private LapBaoGiaRestService() {
        webClientService = new WebClientService();
    }

}
