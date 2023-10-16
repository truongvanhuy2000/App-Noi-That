package com.huy.appnoithat.Service.SessionService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import com.huy.appnoithat.Session.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class UserSessionService {
    final static Logger LOGGER = LogManager.getLogger(UserSessionService.class);
    private static final String SESSION_DIRECTORY = Config.USER.SESSION_DIRECTORY;
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final PersistenceStorageService persistenceStorageService;

    /**
     * Constructor for UserSessionService. Initializes required services and objects.
     */
    public UserSessionService() {
        webClientService = new WebClientServiceImpl();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        persistenceStorageService = PersistenceStorageService.getInstance();
    }

    /**
     * Checks if the user is logged in by validating the session.
     *
     * @return true if the session is valid and the user is logged in, false otherwise.
     */
    public boolean isLogin() {
        return isSessionValid();
    }

    /**
     * Clears the user session data, effectively logging the user out.
     */
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

    /**
     * Sets the user session with the provided username and JWT token.
     *
     * @param username The username of the logged-in user.
     * @param jwtToken The JWT token received upon successful login.
     */
    public void setSession(String username, String jwtToken) {
        setToken(jwtToken);
        if (!username.isEmpty()) {
            String info = webClientService.authorizedHttpGetJson("/api/info?username=" + username, getToken());
            if (info == null) {
                return;
            }
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

    /**
     * Sets the JWT token for the current user session.
     *
     * @param jwtToken The JWT token to set for the user session.
     */
    public void setToken(String jwtToken) {
        UserSession session = UserSession.getInstance();
        session.setJwtToken(jwtToken);
    }

    /**
     * Retrieves the JWT token from the user session.
     *
     * @return The JWT token from the user session.
     */
    public String getToken() {
        UserSession session = UserSession.getInstance();
        return session.getJwtToken();
    }


    /**
     * Sets the logged-in user's account information in the user session.
     *
     * @param account The logged-in user's account information.
     */
    public void setLoginAccount(Account account) {
        UserSession session = UserSession.getInstance();
        session.setAccount(account);
    }


    /**
     * Sets the logged-in user's account information in the user session.
     *
     * @param account The logged-in user's account information.
     */
    public Account getLoginAccount() {
        UserSession session = UserSession.getInstance();
        return session.getAccount();
    }

    /**
     * Gets the username of the currently logged-in user.
     *
     * @return The username of the currently logged-in user.
     */
    public String getUsername() {
        return getLoginAccount().getUsername();
    }


    /**
     * Retrieves the user session of the currently logged-in user.
     *
     * @return The user session of the currently logged-in user.
     * @throws RuntimeException If no session is found (user is not logged in).
     */
    public UserSession getSession() {
        if (!isLogin()) {
            LOGGER.error("No session found");
            throw new RuntimeException("User is not login");
        }
        return UserSession.getInstance();
    }


    /**
     * Checks if the user session is valid by making a request to the server.
     *
     * @return True if the user session is valid, false otherwise.
     * @throws RuntimeException If an error occurs while checking the session validity.
     */
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

    /**
     * Loads the user session data from the disk.
     *
     * @throws IOException If an error occurs while reading the session data from the disk.
     */
    public void loadSessionFromDisk() throws IOException {
        try (InputStream is = new FileInputStream(SESSION_DIRECTORY)) {
            byte[] data = is.readAllBytes();
            try {
                parseSessionJsonObject(new String(data));
            } catch (JsonProcessingException e) {
                LOGGER.error("Error when parsing session json object");
                setToken("");
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Session file not found");
            setToken("");
        }
    }


    /**
     * Saves the user session data to the disk.
     *
     * @throws IOException If an error occurs while writing the session data to the disk.
     */
    public void saveSessionToDisk() throws IOException {
        try {
            String sessionObject = getSessionJsonObject();
            OutputStream os = new FileOutputStream(SESSION_DIRECTORY);
            os.write(sessionObject.getBytes(), 0, sessionObject.length());
            os.close();
        } catch (IOException e) {
            LOGGER.error("Error when saving session to disk");
            // Handle the exception or throw a custom exception if desired.
        }
    }


    /**
     * Creates and returns a JSON representation of the user session data.
     *
     * @return A JSON string representing the user session data.
     * @throws RuntimeException If an error occurs while creating the JSON object.
     */
    private String getSessionJsonObject() {
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


    /**
     * Parses the JSON representation of the user session data and sets the session.
     *
     * @param jsonObject The JSON string representing the user session data.
     * @throws JsonProcessingException If an error occurs while parsing the JSON object.
     */
    private void parseSessionJsonObject(String jsonObject) throws JsonProcessingException {
        if (jsonObject == null) {
            LOGGER.error("jsonObject cannot be null");
            throw new IllegalArgumentException("jsonObject cannot be null");
        }
        ObjectNode node = (ObjectNode) objectMapper.readTree(jsonObject);
        String username = node.get("username").asText();
        String token = node.get("token").asText();
        setSession(username, token);
    }
}
