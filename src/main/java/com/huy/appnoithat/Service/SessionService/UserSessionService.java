package com.huy.appnoithat.Service.SessionService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import com.huy.appnoithat.Session.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class UserSessionService {
    final static Logger LOGGER = LogManager.getLogger(UserSessionService.class);
    private static final String SESSION_DIRECTORY = "/home/huy/Project/Java/AppNoiThat/AppNoiThat/data/userSession/session";
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    public UserSessionService() {
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
    public boolean isLogin() {
        return isSessionValid();
    }
    public void cleanUserSession() {
        UserSession session = UserSession.getInstance();
        session.setAccount(new Account(0, "", "", false, null, new ArrayList<>(), false, null));
        session.setJwtToken("");
        try {
            saveSessionToDisk();
        } catch (IOException e) {
            LOGGER.error("Error when saving session to disk");
            throw new RuntimeException(e);
        }
    }
    public void setSession(String username, String jwtToken) {
        setToken(jwtToken);
        if (!username.isEmpty()) {
            String info = webClientService.authorizedHttpGetJson("/api/info?username=" + username, getToken());
            Account account = null;
            try {
                account = objectMapper.readValue(info, Account.class);
            } catch (JsonProcessingException e) {
                LOGGER.error("Error when parsing account from response");
                throw new RuntimeException(e);
            }
            setLoginAccount(account);
        }
    }
    public void setToken(String jwtToken) {
        UserSession session = UserSession.getInstance();
        session.setJwtToken(jwtToken);
    }
    public String getToken() {
        UserSession session = UserSession.getInstance();
        return session.getJwtToken();
    }
    public void setLoginAccount(Account account) {
        UserSession session = UserSession.getInstance();
        session.setAccount(account);
    }
    public Account getLoginAccount() {
        UserSession session = UserSession.getInstance();
        return session.getAccount();
    }
    public UserSession getSession() {
        if (!isLogin()) {
            LOGGER.error("No session found");
            throw new RuntimeException("User is not login");
        }
        return UserSession.getInstance();
    }
    public boolean isSessionValid() {
        if (getToken().isEmpty()) {
            try {
                loadSessionFromDisk();
            } catch (IOException e) {
                LOGGER.error("Error when loading session from disk");
                throw new RuntimeException(e);
            }
        }
        String response = webClientService.authorizedHttpGetJson("/api/index", getToken());
        return response != null;
    }
    // Haven't implemented yet
    public void loadSessionFromDisk() throws IOException {
        InputStream is = null;
        is = new FileInputStream(SESSION_DIRECTORY);
        byte[] data = is.readAllBytes();
        try {
            parseSessionJsonObject(new String(data));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error when parsing session json object");
            setToken("");
        }
    }
    public void saveSessionToDisk() throws IOException {
        String sessionObject = getSessionJsonObject();
        OutputStream os = new FileOutputStream(SESSION_DIRECTORY);
        os.write(sessionObject.getBytes(), 0, sessionObject.length());
        os.close();
    }
    private String getSessionJsonObject(){
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("username", getLoginAccount().getUsername());
        jsonObject.put("token", getToken());
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error when creating session json object");
            throw new RuntimeException(e);
        }
    }
    private void parseSessionJsonObject(String jsonObject) throws JsonProcessingException {
            ObjectNode node = (ObjectNode) objectMapper.readTree(jsonObject);
            String username = node.get("username").asText();
            String token = node.get("token").asText();
//            if (username.equals("") || token.equals("")) {
//                return;
//            }
            setSession(username, token);
    }
}
