package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.Service.WebClient.JavaNetHttpClient;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import lombok.RequiredArgsConstructor;
import org.codehaus.httpcache4j.uri.URIBuilder;

@RequiredArgsConstructor
public class BangNoiThatRestService {
    private static final String BASE_ENDPOINT = "/api/bangnoithat";
    private final WebClientService webClientService;

    public BangNoiThatRestService() {
        webClientService = JavaNetHttpClient.getInstance();
    }
    public void sampleAll() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("sampleAll");
        this.webClientService.authorizedHttpGet(uriBuilder, String.class);
    }
}
