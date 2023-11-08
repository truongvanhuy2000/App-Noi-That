//package com.huy.appnoithat.Service.WebClient;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.json.JsonMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.huy.appnoithat.Entity.Account;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.ArrayList;
//
//class WebClientServiceImplTest {
//    private WebClientService webClientService;
//    private ObjectMapper objectMapper;
//    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJraW0iLCJpc3MiOiJhcHBub2l0aGF0IiwiaWF0IjoxNjk5NDA5ODEzLCJleHAiOjQyOTE0MDk4MTN9.i2dSJibM-j7SgW15S5kJSiCMIsKgR9ou_HBMvnG_ZKSZS-qZJOKpfq3jAgQmph3CXn44M2yJ29WzFPqwvG8xfQ";
//    private static final String GET = "GET";
//    private static final String POST = "POST";
//    private static final String PUT = "PUT";
//    private static final String DELETE = "DELETE";
//    private static final String CONTENT_TYPE = "Content-Type";
//    private static final String JSON = "application/json";
//    private static final String AUTHORIZATION = "Authorization";
//    @BeforeEach
//    void setUp() {
//        webClientService = new WebClientServiceImpl();
//        objectMapper = JsonMapper.builder()
//                .addModule(new JavaTimeModule())
//                .build();
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void getAccounts() {
//        client = HttpClient.newHttpClient();
//        HttpRequest httpRequest = buildJsonHttpRequest(GET, "", token, "");
//        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//    }
//    private HttpRequest buildJsonHttpRequest(String method, String path, String authenticationToken, String data) {
//        HttpRequest.Builder builder = HttpRequest.newBuilder();
//        builder.uri(URI.create("http://localhost:8080/api/bangnoithat/event/DBModification"));
//        builder.setHeader( "Accept", "text/event-stream" );
//        builder.header(CONTENT_TYPE, JSON);
//
//        switch (method) {
//            case GET -> builder.GET();
//            case POST -> {
//                if (data != null && !data.isEmpty()) {
//                    builder.POST(HttpRequest.BodyPublishers.ofString(data));
//                    break;
//                }
//                throw new IllegalArgumentException("Data cannot be null or empty");
//            }
//            case PUT -> {
//                if (data != null && !data.isEmpty()) {
//                    builder.PUT(HttpRequest.BodyPublishers.ofString(data));
//                    break;
//                }
//                throw new IllegalArgumentException("Data cannot be null or empty");
//            }
//            case DELETE -> builder.DELETE();
//            default -> throw new IllegalArgumentException();
//        }
//        if (authenticationToken != null && !authenticationToken.isEmpty()) {
//            builder.header(AUTHORIZATION, "Bearer " + authenticationToken);
//        }
//        return builder.build();
//    }
//    @Test
//    void getPhongCachs() {
//        String response = webClientService.authorizedHttpGetJson("/api/phongcach", token);
//        System.out.println(response);
//    }
//
//
//    @Test
//    void unauthorizeHttpPostJson() {
//    }
//
//    @Test
//    void unauthorizeHttpGetJson() {
//    }
//
//    @Test
//    void authorizeHttpPostJson() {
//    }
//
//    @Test
//    void authorizeHttpGetJson() {
//    }
//
//    @Test
//    void authorizeHttpPutJson() {
//    }
//
//    @Test
//    void authorizeHttpDeleteJson() {
//    }
//}