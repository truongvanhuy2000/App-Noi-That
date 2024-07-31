package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.DataModel.WebClient.Response;
import com.huy.appnoithat.Service.WebClient.ApacheHttpClient;
import com.huy.appnoithat.Session.UserSessionService;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.codehaus.httpcache4j.uri.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

public class TokenRestServiceIntegrationTest {
    ObjectMapper objectMapper = Mockito.spy(new ObjectMapper());
    UserSessionService userSessionService = Mockito.mock(UserSessionService.class);
    HttpClient httpclient = Mockito.spy(HttpClients.createDefault());
    ApacheHttpClient apacheHttpClient = Mockito.spy(new ApacheHttpClient(httpclient, userSessionService, objectMapper));
    TokenRestService tokenRestService = new TokenRestService(apacheHttpClient);

    @BeforeEach
    void setUp() {
    }

    @Test
    void login() {
        Optional<Token> optionalToken = tokenRestService.login("test", "test");
        assertTrue(optionalToken.isPresent());
        Token token = optionalToken.get();
        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
        given(userSessionService.getToken()).willReturn(token);
        Response<Object> response = heathCheckApi();
        assertTrue(response.isSuccess());
    }

    @Test
    void loginFail() {
        Optional<Token> optionalToken = tokenRestService.login(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        assertTrue(optionalToken.isEmpty());
    }

    @Test
    void refreshToken() {
        Optional<Token> optionalToken = tokenRestService.login("test", "test");
        assertTrue(optionalToken.isPresent());
        Token token = optionalToken.get();
        Optional<Token> optionalToken1 = tokenRestService.refreshToken(token.getRefreshToken());
        assertTrue(optionalToken1.isPresent());
        assertEquals(optionalToken1.get().getRefreshToken(), token.getRefreshToken());
        assertNotEquals(optionalToken1.get().getAccessToken(), token.getAccessToken());
    }

     Response<Object> heathCheckApi() {
        return apacheHttpClient.authorizedHttpGet(URIBuilder.empty().addPath("api", "index"), (TypeReference<Object>) null);
    }
}