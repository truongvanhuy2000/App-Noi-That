package com.huy.appnoithat.Service.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Exception.AccountExpiredException;
import com.huy.appnoithat.Exception.ServerConnectionException;
import com.huy.appnoithat.Handler.ServerResponseHandler;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

public class WebClientService {
    final static Logger LOGGER = LogManager.getLogger(WebClientService.class);
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
    private final UserSessionService userSessionService;
    private final ServerResponseHandler serverResponseHandler;
    private final ObjectMapper objectMapper;
    public WebClientService() {
        this.host = SERVER_ADDRESS;
        this.timeOut = TIME_OUT;
        userSessionService = new UserSessionService();
        client = HttpClient.newHttpClient();
        serverResponseHandler = new ServerResponseHandler();
        objectMapper = new ObjectMapper();
    }
    //    Use this api to do an unauthorized Http Post request.
    //    Path is the path to the api
    //    jsonData is the data to be sent to the server. Must be in json format
    public String unauthorizedHttpPostJson(String path, String jsonData) {
        if (path == null || jsonData == null) {
            throw new IllegalArgumentException();
        }
        HttpResponse<String> response = doSendRequest(POST, path, null, jsonData);
        if (response.statusCode() == 403) {
            return null;
        } else if (response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }

    //    Use this api to do an unauthorized Http GET request.
    //    Path is the path to the api
    public String unauthorizedHttpGetJson(String path) {
        if (path == null) {
            throw new IllegalArgumentException();
        }
        HttpResponse<String> response = doSendRequest(GET, path, null, null);
        if (response.statusCode() == 403) {
            return null;
        } else if (response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }

    //    Use this api to do an authorized Http Post request.
    //    Path is the path to the api, token is the bearer token provided after login
    //    jsonData is the data to be sent to the server. Must be in json format
    public String authorizedHttpPostJson(String path, String jsonData) {
        String token = userSessionService.getJwtToken();
        if (path == null || jsonData == null || token == null) {
            throw new IllegalArgumentException();
        }
        HttpResponse<String> response =  doSendRequest(POST, path, token, jsonData);
        if (response.statusCode() == 403) {
            Token newToken = tryRefreshToken();
            if (newToken == null) {
                return null;
            }
            return authorizedHttpPostJson(path, jsonData);
        } else if (response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }

    //    Use this api to do an authorized Http Get request.
    //    Path is the path to the api, token is the bearer token provided after login
    public String authorizedHttpGetJson(String path) {
        String token = userSessionService.getJwtToken();
        if (path == null || token == null) {
            throw new IllegalArgumentException();
        }
        HttpResponse<String> response =  doSendRequest(GET, path, token, null);
        if (response.statusCode() == 403) {
            Token newToken = tryRefreshToken();
            if (newToken == null) {
                return null;
            }
            return authorizedHttpGetJson(path);
        } else if (response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }

    //    Use this api to do an authorized Http Put request.
    //    Path is the path to the api, token is the bearer token provided after login
    //    jsonData is the data to be sent to the server. Must be in json format
    public String authorizedHttpPutJson(String path, String jsonData) {
        String token = userSessionService.getJwtToken();
        if (path == null || jsonData == null || token == null) {
            throw new IllegalArgumentException();
        }
        HttpResponse<String> response =  doSendRequest(PUT, path, token, jsonData);
        if (response.statusCode() == 403) {
            Token newToken = tryRefreshToken();
            if (newToken == null) {
                return null;
            }
            return authorizedHttpPutJson(path, jsonData);
        } else if (response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }

    //    Use this api to do an authorized Http Delete request.
    //    Path is the path to the api, token is the bearer token provided after login
    public String authorizedHttpDeleteJson(String path, String jsonData) {
        String token = userSessionService.getJwtToken();
        if (path == null || jsonData == null || token == null) {
            throw new IllegalArgumentException();
        }
        HttpResponse<String> response =  doSendRequest(DELETE, path, token, jsonData);
        if (response.statusCode() == 403) {
            Token newToken = tryRefreshToken();
            if (newToken == null) {
                return null;
            }
            return authorizedHttpDeleteJson(path, jsonData);
        } else if (response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }
    private HttpResponse<String> doSendRequest(String method, String path, String authenticationToken, String data) {
        try {
            if ((method.equals("PUT") || method.equals("POST")) && data.isEmpty()) {
                data = " ";
            }
            HttpRequest httpRequest = buildJsonHttpRequest(method, path, authenticationToken, data);
            return client.send(httpRequest, BodyHandlers.ofString());
        } catch (Exception e) {
            LOGGER.error("Error when sending request to server" + method + " " + path + " " + authenticationToken + " " + data);
            throw new ServerConnectionException(e);
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
    private Token tryRefreshToken() {
        LOGGER.info("Trying to refresh jwt token");
        String refreshToken = userSessionService.getRefreshToken();
        if (refreshToken == null || refreshToken.isEmpty()) {
            serverResponseHandler.handleTokenExpired(userSessionService::cleanUserSession);
            throw new AccountExpiredException("Refresh token expired, need to login again");
        }
        try {
            Map<String, String> data = Map.of("refreshToken", refreshToken);
            HttpResponse<String> response = doSendRequest(
                    POST, "/api/refreshToken", null, objectMapper.writeValueAsString(data));
            if (response.statusCode() != 200) {
                LOGGER.info("Refresh token expired, need to login again");
                serverResponseHandler.handleTokenExpired(userSessionService::cleanUserSession);
                throw new AccountExpiredException("Refresh token expired, need to login again");
            }
            Token token = objectMapper.readValue(response.body(), Token.class);
            userSessionService.setToken(token);
            userSessionService.saveSessionToDisk();
            return token;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
