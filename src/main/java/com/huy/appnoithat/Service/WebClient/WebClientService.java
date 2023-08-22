package com.huy.appnoithat.Service.WebClient;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class WebClientService {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private String host;
    private long timeOut;
    private HttpClient client;
    public WebClientService(String host, long timeOut) {
        this.host = host;
        this.timeOut = timeOut;
        client = HttpClient.newHttpClient();
    }

    public String unauthorizeHttpPostJson(String path, String jsonData) {
        if (path == null || jsonData == null) {
            throw new IllegalArgumentException();
        }
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(this.host + path))
                    .timeout(java.time.Duration.ofSeconds(this.timeOut))
                    .header(CONTENT_TYPE, JSON)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .build();
            HttpResponse<String> response = client.send(httpRequest, BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public String unauthorizeHttpGetJson(String path) {
        if (path == null) {
            throw new IllegalArgumentException();
        }
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(this.host + path))
                    .timeout(java.time.Duration.ofSeconds(this.timeOut))
                    .header(CONTENT_TYPE, JSON)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(httpRequest, BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public String authorizeHttpGetJson(String path, String token) {
        try {
            if (path == null) {
                throw new IllegalArgumentException();
            }
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(this.host + path))
                    .timeout(java.time.Duration.ofSeconds(this.timeOut))
                    .header(CONTENT_TYPE, JSON)
                    .header(AUTHORIZATION, "Bearer " + token)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(httpRequest, BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public String authorizeHttpPutJson(String path, String jsonData, String token) {
        try {
            if (path == null) {
                throw new IllegalArgumentException();
            }
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(this.host + path))
                    .timeout(java.time.Duration.ofSeconds(this.timeOut))
                    .header(CONTENT_TYPE, JSON)
                    .header(AUTHORIZATION, "Bearer " + token)
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonData))
                    .build();
            HttpResponse<String> response = client.send(httpRequest, BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
