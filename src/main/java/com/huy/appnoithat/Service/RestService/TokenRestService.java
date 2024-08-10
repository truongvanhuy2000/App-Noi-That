package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.DataModel.WebClient.Response;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class TokenRestService {
    private static class Path {
        private static final URI BASE_URL = URIBuilder.empty().addPath("api").toURI();
        public static final URI REFRESH_TOKEN = URIBuilder.fromURI(BASE_URL).addPath("refreshToken").toURI();
        public static final URI LOGIN = URIBuilder.fromURI(BASE_URL).addPath("login").toURI();
    }
    private final WebClientService webClientService;

    public Optional<Token> refreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            return Optional.empty();
        }
        Map<String, String> body = Map.of("refreshToken", refreshToken);
        URIBuilder uriBuilder = URIBuilder.fromURI(Path.REFRESH_TOKEN);
        TypeReference<Token> typeReference = new TypeReference<>() {
        };
        Response<Token> response = this.webClientService.unauthorizedHttpPost(uriBuilder, body, typeReference);
        if (response.isSuccess()) {
            return response.getResponse();
        } else {
            // TODO: Handle error response
            return Optional.empty();
        }
    }

    public Optional<Token> login(String username, String password) {
        URIBuilder uriBuilder = URIBuilder.fromURI(Path.LOGIN);
        TypeReference<Token> typeReference = new TypeReference<>() {};
        Account account = Account.builder().username(username).password(password).build();
        Response<Token> response = this.webClientService.unauthorizedHttpPost(uriBuilder, account, typeReference);
        if (response.isSuccess()) {
            return response.getResponse();
        } else {
            // TODO: Handle error response
            return Optional.empty();
        }
    }
}
