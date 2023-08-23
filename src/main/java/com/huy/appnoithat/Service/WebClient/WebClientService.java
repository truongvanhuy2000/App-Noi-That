package com.huy.appnoithat.Service.WebClient;

public interface WebClientService {
    String unauthorizedHttpPostJson(String path, String jsonData);
    String unauthorizedHttpGetJson(String path);
    String authorizedHttpPostJson(String path, String jsonData, String token);
    String authorizedHttpGetJson(String path, String token);
    String authorizedHttpPutJson(String path, String jsonData, String token);
    String authorizedHttpDeleteJson(String path, String jsonData, String token);

}
