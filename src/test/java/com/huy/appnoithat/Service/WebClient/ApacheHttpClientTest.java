package com.huy.appnoithat.Service.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.DataModel.WebClient.Response;
import com.huy.appnoithat.Session.UserSessionManagerImpl;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.codehaus.httpcache4j.uri.URIBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

class ApacheHttpClientTest {
    private final HttpClient httpclient = Mockito.mock(CloseableHttpClient.class);
    private final UserSessionManagerImpl userSessionManagerImpl = Mockito.mock();
    private final ObjectMapper objectMapper = Mockito.mock();
    private final ApacheHttpClient apacheHttpClient = new ApacheHttpClient(httpclient, userSessionManagerImpl, objectMapper);

    @Test
    void authorizedPostMultipartUpload() {
    }

    @Test
    void authorizedPutMultipartUpload() {
    }

    @Test
    void authorizedHttpGet_happyPath() throws IOException, ProtocolException {
        Token token = Token.builder()
                .refreshToken("test")
                .accessToken("test")
                .build();
        given(userSessionManagerImpl.getToken()).willReturn(token);
        given(userSessionManagerImpl.isAccessTokenExpired()).willReturn(false);

        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        given(httpclient.execute(any(ClassicHttpRequest.class), any(HttpClientResponseHandler.class)))
                .willReturn(Mockito.mock(Response.class));

        URIBuilder uri = URIBuilder.empty();
        TypeReference<Object> typeReference = Mockito.mock();

        apacheHttpClient.authorizedHttpGet(uri, typeReference);

        verify(userSessionManagerImpl, times(1)).getToken();
        verify(httpclient, times(1)).execute(any(HttpGet.class), any(HttpClientResponseHandler.class));
        verify(httpclient).execute(captor.capture(), any(HttpClientResponseHandler.class));
        HttpGet httpGet = captor.getValue();
        assertNull(httpGet.getEntity());
        assertEquals(httpGet.getHeader(HttpHeaders.AUTHORIZATION).getValue(), "Bearer " + token.getAccessToken());
    }

    @Test
    void authorizedHttpGet_noToken() {
        given(userSessionManagerImpl.getToken()).willReturn(null);
        URIBuilder uri = URIBuilder.empty();
        TypeReference<Object> typeReference = Mockito.mock();
        Response<Object> response = apacheHttpClient.authorizedHttpGet(uri, typeReference);
        verify(userSessionManagerImpl, times(1)).getToken();
        assertFalse(response.isSuccess());
        assertTrue(response.getResponse().isEmpty());
        assertTrue(response.getRawResponse().isEmpty());
        assertTrue(response.getErrorResponse().isEmpty());
    }

    @Test
    void authorizedHttpGet_httpClientThrowIOException() throws IOException {
        Token token = Token.builder()
                .refreshToken("test")
                .accessToken("test")
                .build();
        given(userSessionManagerImpl.getToken()).willReturn(token);
        given(userSessionManagerImpl.isAccessTokenExpired()).willReturn(false);

        given(httpclient.execute(any(ClassicHttpRequest.class), any(HttpClientResponseHandler.class)))
                .willThrow(IOException.class);
        URIBuilder uri = URIBuilder.empty();
        TypeReference<Object> typeReference = Mockito.mock();
        Response<Object> response = apacheHttpClient.authorizedHttpGet(uri, typeReference);
        verify(userSessionManagerImpl, times(1)).getToken();
        assertFalse(response.isSuccess());
        assertTrue(response.getResponse().isEmpty());
        assertTrue(response.getRawResponse().isEmpty());
        assertTrue(response.getErrorResponse().isEmpty());
    }

    @Test
    void authorizedHttpPost_happyPath() throws IOException, ProtocolException {
        String serializedObject = "test";
        Token token = Token.builder()
                .refreshToken("test")
                .accessToken("test")
                .build();
        given(userSessionManagerImpl.getToken()).willReturn(token);
        given(userSessionManagerImpl.isAccessTokenExpired()).willReturn(false);
        ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);

        given(httpclient.execute(any(ClassicHttpRequest.class), any(HttpClientResponseHandler.class)))
                .willReturn(Mockito.mock(Response.class));
        given(objectMapper.writeValueAsString(any())).willReturn(serializedObject);

        URIBuilder uri = URIBuilder.empty();
        TypeReference<Object> typeReference = Mockito.mock();
        apacheHttpClient.authorizedHttpPost(uri, Mockito.mock(Object.class), typeReference);

        verify(userSessionManagerImpl, times(1)).getToken();
        verify(httpclient, times(1)).execute(any(HttpPost.class), any(HttpClientResponseHandler.class));
        verify(httpclient).execute(captor.capture(), any(HttpClientResponseHandler.class));

        HttpPost httpPost = captor.getValue();
        assertEquals(new String(httpPost.getEntity().getContent().readAllBytes()), serializedObject);
        assertEquals(httpPost.getHeader(HttpHeaders.AUTHORIZATION).getValue(), "Bearer " + token.getRefreshToken());
    }

    @Test
    void authorizedHttpDelete() {
    }

    @Test
    void authorizedHttpPut() {
    }

    @Test
    void authorizedHttpGetFile() {
    }

    @Test
    void unauthorizedHttpPost() {

    }

    @Test
    void doHttpRequest() {
    }

    @Test
    void handleResponse_statusCode200() throws IOException {
        Response<Object> restResponse = Response.builder().build();
        ClassicHttpResponse classicHttpResponse = Mockito.mock(CloseableHttpResponse.class);
        TypeReference<Object> typeReference = Mockito.mock();
        HttpEntity httpEntity = Mockito.mock(InputStreamEntity.class);
        Object body = Mockito.mock(Object.class);
        long contentLength = 10 * 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        given(objectMapper.readValue(any(InputStream.class), eq(typeReference))).willReturn(body);
        given(classicHttpResponse.getCode()).willReturn(HttpStatus.SC_OK);
        given(classicHttpResponse.getEntity()).willReturn(httpEntity);
        given(httpEntity.getContent()).willReturn(byteArrayInputStream);

        Response<Object> response = apacheHttpClient.handleResponse(classicHttpResponse, restResponse, typeReference);
        assertTrue(response.getResponse().isPresent());
        assertEquals(response.getResponse().get(), body);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrorResponse().isEmpty());
        assertTrue(response.getRawResponse().isEmpty());
    }

    @Test
    void prepareMultipartEntity() {
    }
}