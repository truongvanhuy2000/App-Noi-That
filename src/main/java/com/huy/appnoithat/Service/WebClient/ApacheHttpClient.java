package com.huy.appnoithat.Service.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.DataModel.MultipartForm;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.DataModel.WebClient.ErrorResponse;
import com.huy.appnoithat.DataModel.WebClient.Response;
import com.huy.appnoithat.Handler.SessionExpiredHandler;
import com.huy.appnoithat.Session.UserSessionManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.mime.ContentBody;
import org.apache.hc.client5.http.entity.mime.InputStreamBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ApacheHttpClient implements WebClientService {
    final static Logger LOGGER = LogManager.getLogger(ApacheHttpClient.class);

    private static final String SERVER_ADDRESS = Config.WEB_CLIENT.BASE_URL;
    public static final URI REFRESH_TOKEN = URIBuilder.empty().addPath("api", "refreshToken").toURI();

    private final HttpClient httpclient;
    private final UserSessionManager userSessionManager;
    private final ObjectMapper objectMapper;
    private final SessionExpiredHandler sessionExpiredHandler;

    @Override
    public <X> Response<X> authorizedPostMultipartUpload(URIBuilder uri, MultipartForm multipartForm, TypeReference<X> typeReference) {
        URI fullURI = uri.withHost(SERVER_ADDRESS).toURI();
        HttpPost httpPost = new HttpPost(fullURI);
        HttpEntity multipart = prepareMultipartEntity(multipartForm);
        return doHttpRequest(httpPost, multipart, true, typeReference);
    }

    @Override
    public <X> Response<X> authorizedPutMultipartUpload(URIBuilder uri, MultipartForm multipartForm, TypeReference<X> typeReference) {
        URI fullURI = uri.withHost(SERVER_ADDRESS).toURI();
        HttpPut httpPut = new HttpPut(fullURI);
        HttpEntity multipart = prepareMultipartEntity(multipartForm);
        return doHttpRequest(httpPut, multipart, true, typeReference);
    }

    @Override
    public <X> Response<X> authorizedHttpGet(URIBuilder uri, TypeReference<X> typeReference) {
        URI fullURI = uri.withHost(SERVER_ADDRESS).toURI();
        HttpGet httpGet = new HttpGet(fullURI);
        return doHttpRequest(httpGet, null, true, typeReference);
    }

    @Override
    public <X> Response<X> authorizedHttpPost(URIBuilder uri, Object body, TypeReference<X> typeReference) {
        URI fullURI = uri.withHost(SERVER_ADDRESS).toURI();
        HttpPost httpPost = new HttpPost(fullURI);
        try {
            HttpEntity httpEntity = null;
            if (body != null) {
                httpEntity = new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON);
            }
            return doHttpRequest(httpPost, httpEntity, true, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <X> Response<X> authorizedHttpDelete(URIBuilder uri, TypeReference<X> typeReference) {
        URI fullURI = uri.withHost(SERVER_ADDRESS).toURI();
        ClassicHttpRequest httpDelete = new HttpDelete(fullURI);
        return doHttpRequest(httpDelete, null, true, typeReference);
    }

    @Override
    public <X> Response<X> authorizedHttpPut(URIBuilder uri, Object body, TypeReference<X> typeReference) {
        URI fullURI = uri.withHost(SERVER_ADDRESS).toURI();
        ClassicHttpRequest httpPut = new HttpPut(fullURI);
        try {
            HttpEntity httpEntity = null;
            if (body != null) {
                httpEntity = new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON);
            }
            return doHttpRequest(httpPut, httpEntity, true, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response<InputStream> authorizedHttpGetFile(URIBuilder uri) {
        URI fullURI = uri.withHost(SERVER_ADDRESS).toURI();
        HttpGet httpGet = new HttpGet(fullURI);
        return doHttpRequest(httpGet, null, true, null);
    }

    public <X> Response<X> unauthorizedHttpPost(URIBuilder uri, Object body, TypeReference<X> typeReference) {
        URI fullURI = uri.withHost(SERVER_ADDRESS).toURI();
        HttpPost httpPost = new HttpPost(fullURI);
        try {
            HttpEntity httpEntity = null;
            if (body != null) {
                httpEntity = new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON);
            }
            return doHttpRequest(httpPost, httpEntity, false, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <X> Response<X> doHttpRequest(
            ClassicHttpRequest classicHttpRequest,
            HttpEntity entity,
            boolean requireAuthentication,
            TypeReference<X> typeReference
    ) {
        Response<X> restResponse = Response.<X>builder().build();
        if (requireAuthentication) {
            Optional<String> token = getAndCheckForTokenExpiration();
            if (token.isEmpty()) {
                return restResponse.toBuilder().isSuccess(false).build();
            }
            classicHttpRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token.get());
        }
        if (entity != null) {
            classicHttpRequest.setEntity(entity);
        }
        try {
            return httpclient.execute(classicHttpRequest, response -> handleResponse(response, restResponse, typeReference));
        } catch (IOException ioException) {
            restResponse.setSuccess(false);
            LOGGER.error(ioException);
            return restResponse;
        }
    }

    public <X> Response<X> handleResponse(ClassicHttpResponse response, Response<X> restResponse, TypeReference<X> typeReference) {
        try {
            HttpEntity httpEntity = response.getEntity();
            if (response.getCode() == HttpStatus.SC_OK) {
                restResponse.setSuccess(true);
                if (typeReference == null) {
                    restResponse.setRawResponse(Optional.of(ByteBuffer.wrap(httpEntity.getContent().readAllBytes())));
                } else {
                    restResponse.setResponse(Optional.of(objectMapper.readValue(httpEntity.getContent(), typeReference)));
                }
            } else {
                restResponse.setSuccess(false);
                restResponse.setErrorResponse(Optional.of(objectMapper.readValue(httpEntity.getContent(), ErrorResponse.class)));
            }
        } catch (IOException ioException) {
            restResponse.setSuccess(false);
            LOGGER.error(ioException);
        }
        return restResponse;
    }

    public HttpEntity prepareMultipartEntity(MultipartForm multipartForm) {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        if (multipartForm.getMultipartMap() != null) {
            multipartForm.getMultipartMap().forEach((key, value) -> {
                ContentBody contentBody = new InputStreamBody(value.getInputStream(), value.getFileName());
                multipartEntityBuilder.addPart(key, contentBody);
            });
        }
        if (multipartForm.getParams() != null) {
            multipartForm.getParams().forEach((key, value) -> {
                multipartEntityBuilder.addParameter(new BasicNameValuePair(key, value));
            });
        }
        return multipartEntityBuilder.build();
    }

    private Optional<String> getAndCheckForTokenExpiration() {
        Token token = userSessionManager.getToken();
        if (token == null) {
            LOGGER.error("Token is null");
            return Optional.empty();
        }
        if (!userSessionManager.isAccessTokenExpired()) {
            return Optional.of(token.getAccessToken());
        }
        if (userSessionManager.isRefreshTokenExpired()) {
            sessionExpiredHandler.handleTokenExpired();
            return Optional.empty();
        }
        Optional<Token> newToken = tryRefreshingToken(token.getRefreshToken());
        return newToken.map(Token::getAccessToken);
    }

    private Optional<Token> tryRefreshingToken(String refreshToken) {
        if (StringUtils.isEmpty(refreshToken)) {
            return Optional.empty();
        }
        Map<String, String> body = Map.of("refreshToken", refreshToken);
        URIBuilder uriBuilder = URIBuilder.fromURI(REFRESH_TOKEN);
        TypeReference<Token> typeReference = new TypeReference<>() {};
        Response<Token> response = unauthorizedHttpPost(uriBuilder, body, typeReference);
        if (!response.isSuccess() || response.getResponse().isEmpty()) {
            sessionExpiredHandler.handleTokenExpired();
            return Optional.empty();
        }
        userSessionManager.saveToken(response.getResponse().get());
        return response.getResponse();
    }
}
