package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.Service.WebClient.WebClientService;
import org.codehaus.httpcache4j.uri.URIBuilder;

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
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("sampleAll");
        this.webClientService.authorizedHttpGetJson(uriBuilder, String.class);
    }
}
