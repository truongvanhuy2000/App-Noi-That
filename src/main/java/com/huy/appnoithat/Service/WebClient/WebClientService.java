package com.huy.appnoithat.Service.WebClient;

public interface WebClientService {
    //    Use this api to do an unauthorized Http Post request.
    //    Path is the path to the api
    //    jsonData is the data to be sent to the server. Must be in json format
    String unauthorizedHttpPostJson(String path, String jsonData);

    //    Use this api to do an unauthorized Http GET request.
    //    Path is the path to the api
    String unauthorizedHttpGetJson(String path);

    //    Use this api to do an authorized Http Post request.
    //    Path is the path to the api, token is the bearer token provided after login
    //    jsonData is the data to be sent to the server. Must be in json format
    String authorizedHttpPostJson(String path, String jsonData, String token);

    //    Use this api to do an authorized Http Get request.
    //    Path is the path to the api, token is the bearer token provided after login
    String authorizedHttpGetJson(String path, String token);

    //    Use this api to do an authorized Http Put request.
    //    Path is the path to the api, token is the bearer token provided after login
    //    jsonData is the data to be sent to the server. Must be in json format
    String authorizedHttpPutJson(String path, String jsonData, String token);

    //    Use this api to do an authorized Http Delete request.
    //    Path is the path to the api, token is the bearer token provided after login
    String authorizedHttpDeleteJson(String path, String jsonData, String token);

}
