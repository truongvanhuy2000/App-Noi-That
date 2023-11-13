package com.huy.appnoithat.Service.WebClient;

import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Exception.NotAuthorizedException;
import com.huy.appnoithat.Exception.ServerConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class WebClientServiceImpl implements WebClientService {
    final static Logger LOGGER = LogManager.getLogger(WebClientServiceImpl.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String SERVER_ADDRESS = Config.WEB_CLIENT.BASE_URL;
    private static final long TIME_OUT = Config.WEB_CLIENT.TIME_OUT;
    private final String host;
    private final long timeOut;
    private final HttpClient client;

    public WebClientServiceImpl() {
        this.host = SERVER_ADDRESS;
        this.timeOut = TIME_OUT;
        client = HttpClient.newHttpClient();
    }
    //    Use this api to do an unauthorized Http Post request.
    //    Path is the path to the api
    //    jsonData is the data to be sent to the server. Must be in json format
    public String unauthorizedHttpPostJson(String path, String jsonData) {
        if (path == null || jsonData == null) {
            throw new IllegalArgumentException();
        }
        return doSendRequest(POST, path, null, jsonData);
    }

    //    Use this api to do an unauthorized Http GET request.
    //    Path is the path to the api
    public String unauthorizedHttpGetJson(String path) {
        if (path == null) {
            throw new IllegalArgumentException();
        }
        return doSendRequest(GET, path, null, null);
    }

    //    Use this api to do an authorized Http Post request.
    //    Path is the path to the api, token is the bearer token provided after login
    //    jsonData is the data to be sent to the server. Must be in json format
    public String authorizedHttpPostJson(String path, String jsonData, String token) {
        if (path == null || jsonData == null || token == null) {
            throw new IllegalArgumentException();
        }
        return doSendRequest(POST, path, token, jsonData);
    }

    //    Use this api to do an authorized Http Get request.
    //    Path is the path to the api, token is the bearer token provided after login
    public String authorizedHttpGetJson(String path, String token) {
        if (path == null || token == null) {
            throw new IllegalArgumentException();
        }
        return doSendRequest(GET, path, token, null);
    }

    //    Use this api to do an authorized Http Put request.
    //    Path is the path to the api, token is the bearer token provided after login
    //    jsonData is the data to be sent to the server. Must be in json format
    public String authorizedHttpPutJson(String path, String jsonData, String token) {
        if (path == null || jsonData == null || token == null) {
            throw new IllegalArgumentException();
        }
        return doSendRequest(PUT, path, token, jsonData);
    }

    //    Use this api to do an authorized Http Delete request.
    //    Path is the path to the api, token is the bearer token provided after login
    public String authorizedHttpDeleteJson(String path, String jsonData, String token) {
        if (path == null || jsonData == null || token == null) {
            throw new IllegalArgumentException();
        }
        return doSendRequest(DELETE, path, token, jsonData);
    }
    private String doSendRequest(String method, String path, String authenticationToken, String data) {
        try {
            if ((method.equals("PUT") || method.equals("POST")) && data.isEmpty()) {
                data = " ";
            }
            HttpRequest httpRequest = buildJsonHttpRequest(method, path, authenticationToken, data);
            HttpResponse<String> response = client.send(httpRequest, BodyHandlers.ofString());
            switch (response.statusCode()) {
                case 403 -> {
                    LOGGER.error("Error when sending request to server" + method + " " + path + " " + authenticationToken + " " + data);
                    throw new NotAuthorizedException("Access denied");
                }
                case 404 -> {
                    LOGGER.error("Error when sending request to server" + method + " " + path + " " + authenticationToken + " " + data);
                    throw new ServerConnectionException("Resource not found");
                }
                case 200 -> {
                    return response.body();
                }
                default -> {
                    throw new ServerConnectionException("Server error");
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error when sending request to server" + method + " " + path + " " + authenticationToken + " " + data);
            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest buildJsonHttpRequest(String method, String path, String authenticationToken, String data) {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(this.host + path));
        builder.timeout(java.time.Duration.ofSeconds(this.timeOut));
        builder.header(CONTENT_TYPE, JSON);

        switch (method) {
            case GET -> builder.GET();
            case POST -> {
                if (data != null && !data.isEmpty()) {
                    builder.POST(HttpRequest.BodyPublishers.ofString(data));
                    break;
                }
                throw new IllegalArgumentException("Data cannot be null or empty");
            }
            case PUT -> {
                if (data != null && !data.isEmpty()) {
                    builder.PUT(HttpRequest.BodyPublishers.ofString(data));
                    break;
                }
                throw new IllegalArgumentException("Data cannot be null or empty");
            }
            case DELETE -> builder.DELETE();
            default -> throw new IllegalArgumentException();
        }
        if (authenticationToken != null && !authenticationToken.isEmpty()) {
            builder.header(AUTHORIZATION, "Bearer " + authenticationToken);
        }
        return builder.build();
    }
}
