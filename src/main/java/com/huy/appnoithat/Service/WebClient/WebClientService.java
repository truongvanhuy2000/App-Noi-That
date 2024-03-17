package com.huy.appnoithat.Service.WebClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Handler.ServerResponseHandler;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private final long timeOut;
    private final HttpClient client;
    private final UserSessionService userSessionService;
    private final ServerResponseHandler serverResponseHandler;
    private final ObjectMapper objectMapper;

    public WebClientService() {
        this.timeOut = TIME_OUT;
        userSessionService = new UserSessionService();
        client = HttpClient.newHttpClient();
        serverResponseHandler = new ServerResponseHandler();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build();
    }

    public <T> Optional<T> unauthorizedHttpPostJson(@NonNull URIBuilder uri,
                                                    @NonNull Object data, @NonNull Class<T> responseClass) {
        HttpResponse<String> response = doSendRequest(POST, uri, null, data);
        if (response == null) {
            return Optional.empty();
        }
        if (response.statusCode() == 200) {
            return Optional.of(deserializeResponse(response.body(), responseClass));
        } else {
            return Optional.empty();
        }
    }

    public <T> Optional<T> unauthorizedHttpGetJson(@NonNull URIBuilder uri, @NonNull Class<T> responseClass) {
        HttpResponse<String> response = doSendRequest(GET, uri, null, null);
        if (response == null) {
            return Optional.empty();
        }
        if (response.statusCode() == 200) {
            return Optional.of(deserializeResponse(response.body(), responseClass));
        }
        return Optional.empty();
    }

    public <T> Optional<T> authorizedHttpPostJson(@NonNull URIBuilder uri, @NonNull Object data, @NonNull Class<T> responseClass) {
        String token = userSessionService.getJwtToken();
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        }
        HttpResponse<String> response = doSendRequest(POST, uri, token, data);
        if (response == null) {
            return Optional.empty();
        }
        switch (response.statusCode()) {
            case 403 -> {
                Optional<Token> newToken = tryRefreshToken();
                if (newToken.isEmpty()) {
                    return Optional.empty();
                }
                return authorizedHttpPostJson(uri, data, responseClass);
            }
            case 200 -> {
                return Optional.of(deserializeResponse(response.body(), responseClass));
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    public <T> Optional<T> authorizedHttpGetJson(@NonNull URIBuilder uri, @NonNull Class<T> responseClass) {
        String token = userSessionService.getJwtToken();
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        }
        HttpResponse<String> response = doSendRequest(GET, uri, token, null);
        if (response == null) {
            return Optional.empty();
        }
        switch (response.statusCode()) {
            case 403 -> {
                Optional<Token> newToken = tryRefreshToken();
                if (newToken.isEmpty()) {
                    return Optional.empty();
                }
                return authorizedHttpGetJson(uri, responseClass);
            }
            case 200 -> {
                return Optional.of(deserializeResponse(response.body(), responseClass));
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    public <T> Optional<List<T>> authorizedHttpGetJson(@NonNull URIBuilder uri, @NonNull Class<T> responseClass,
                                                       @NonNull Class<? extends Collection> collectionClass) {
        String token = userSessionService.getJwtToken();
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        }
        HttpResponse<String> response = doSendRequest(GET, uri, token, null);
        if (response == null) {
            return Optional.empty();
        }
        switch (response.statusCode()) {
            case 403 -> {
                Optional<Token> newToken = tryRefreshToken();
                if (newToken.isEmpty()) {
                    return Optional.empty();
                }
                return authorizedHttpGetJson(uri, responseClass, collectionClass);
            }
            case 200 -> {
                return Optional.of(deserializeResponse(response.body(), responseClass, collectionClass));
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    public <T> Optional<T> authorizedHttpPutJson(@NonNull URIBuilder uri, @NonNull Object data, @NonNull Class<T> responseClass) {
        String token = userSessionService.getJwtToken();
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        }
        HttpResponse<String> response = doSendRequest(PUT, uri, token, data);
        if (response == null) {
            return Optional.empty();
        }
        switch (response.statusCode()) {
            case 403 -> {
                Optional<Token> newToken = tryRefreshToken();
                if (newToken.isEmpty()) {
                    return Optional.empty();
                }
                return authorizedHttpPutJson(uri, data, responseClass);
            }
            case 200 -> {
                return Optional.of(deserializeResponse(response.body(), responseClass));
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    public <T> Optional<T> authorizedHttpDeleteJson(@NonNull URIBuilder uri, @NonNull Class<T> responseClass) {
        String token = userSessionService.getJwtToken();
        HttpResponse<String> response = doSendRequest(DELETE, uri, token, null);
        if (response == null) {
            return Optional.empty();
        }
        switch (response.statusCode()) {
            case 403 -> {
                Optional<Token> newToken = tryRefreshToken();
                if (newToken.isEmpty()) {
                    return Optional.empty();
                }
                return authorizedHttpDeleteJson(uri, responseClass);
            }
            case 200 -> {
                return Optional.of(deserializeResponse(response.body(), responseClass));
            }
            default -> {
                return Optional.empty();
            }
        }
    }

    private <T> T deserializeResponse(String response, Class<T> objectClass) {
        if (objectClass == String.class) {
            return objectClass.cast(response);
        }
        try {
            return objectMapper.readValue(response, objectClass);
        } catch (Exception e) {
            LOGGER.error("Error when deserialize response from server");
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> deserializeResponse(String response, Class<T> objectClass, Class<? extends Collection> collectionClass) {
        try {
            return objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(collectionClass, objectClass));
        } catch (Exception e) {
            LOGGER.error("Error when deserialize response from server");
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<String> doSendRequest(@NonNull String method, @NonNull URIBuilder uri,
                                               String authenticationToken, Object data) {
        try {
            HttpRequest httpRequest = buildJsonHttpRequest(method, uri, authenticationToken, data);
            return client.send(httpRequest, BodyHandlers.ofString());
        } catch (Exception e) {
            LOGGER.error("Error when sending request to server" + method + " " + uri + " " + authenticationToken + " " + data);
            return null;
        }
    }

    private HttpRequest buildJsonHttpRequest(String method, URIBuilder uri, String authenticationToken, Object data)
            throws JsonProcessingException {
        URI fullAddress = uri.withHost(SERVER_ADDRESS).toURI();
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(fullAddress);
        builder.timeout(java.time.Duration.ofSeconds(this.timeOut));
        builder.header(CONTENT_TYPE, JSON);

        switch (method) {
            case GET -> builder.GET();
            case POST -> {
                String requestBody = objectMapper.writeValueAsString(data);
                builder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
            }
            case PUT -> {
                String requestBody = objectMapper.writeValueAsString(data);
                builder.PUT(HttpRequest.BodyPublishers.ofString(requestBody));
            }
            case DELETE -> builder.DELETE();
            default -> throw new IllegalArgumentException();
        }
        if (StringUtils.isNotBlank(authenticationToken)) {
            builder.header(AUTHORIZATION, "Bearer " + authenticationToken);
        }
        return builder.build();
    }

    private Optional<Token> tryRefreshToken() {
        LOGGER.info("Refreshing token");
        String refreshToken = userSessionService.getRefreshToken();
        if (StringUtils.isBlank(refreshToken)) {
            serverResponseHandler.handleTokenExpired();
            return Optional.empty();
        }
        HttpResponse<String> response = doSendRequest(POST, URIBuilder.empty().addPath("api", "refreshToken"),
                null, Map.of("refreshToken", refreshToken));
        if (response == null) {
            return Optional.empty();
        }
        if (response.statusCode() == 200) {
            Token token = deserializeResponse(response.body(), Token.class);
            userSessionService.setToken(token);
            userSessionService.saveSessionToDisk();
            return Optional.of(token);
        } else {
            LOGGER.info("Refresh token expired, need to login again");
            serverResponseHandler.handleTokenExpired();
            return Optional.empty();
        }
    }
}
