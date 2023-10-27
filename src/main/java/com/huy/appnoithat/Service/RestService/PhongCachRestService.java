package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhongCachRestService {
    private static PhongCachRestService instance;
    final static Logger LOGGER = LogManager.getLogger(PhongCachRestService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService userSessionService;
    private static final String BASE_ENDPOINT = "/api/phongcach";
    private static final String ID_TEMPLATE = "/%d";
    private static final String OWNER_TEMPLATE = "?owner=%s";
    private static final String NAME_TEMPLATE = "&name=%s";
    public static synchronized PhongCachRestService getInstance() {
        if (instance == null) {
            instance = new PhongCachRestService();
        }
        return instance;
    }

    /**
     * Service class for managing PhongCachNoiThat objects via REST API.
     */
    private PhongCachRestService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    /**
     * Retrieves a list of all PhongCachNoiThat objects associated with the current user.
     *
     * @return A list of PhongCachNoiThat objects.
     * @throws RuntimeException if there is an error when retrieving PhongCachNoiThat objects.
     */
    public List<PhongCachNoiThat> findAll() {
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE, userSessionService.getUsername());
        String response = webClientService.authorizedHttpGetJson(path, userSessionService.getToken());
        if (response == null) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(response,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PhongCachNoiThat.class));
        } catch (JsonProcessingException e) {
            LOGGER.error("Can't parse response from server");
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a PhongCachNoiThat object by its ID.
     *
     * @param id The ID of the PhongCachNoiThat to find.
     * @return The PhongCachNoiThat object with the specified ID, or null if not found.
     * @throws RuntimeException if there is an error when finding the PhongCachNoiThat object.
     */
    public PhongCachNoiThat findById(int id) {
        String path = String.format(BASE_ENDPOINT + ID_TEMPLATE + OWNER_TEMPLATE, id, userSessionService.getUsername());
        String response = webClientService.authorizedHttpGetJson(path, userSessionService.getToken());
        if (response == null) {
            return null;
        }
        try {
            return objectMapper.readValue(response, PhongCachNoiThat.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Can't parse response from server");
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a PhongCachNoiThat object by its name.
     *
     * @param name The name of the PhongCachNoiThat to find.
     * @return The PhongCachNoiThat object with the specified name, or null if not found.
     * @throws RuntimeException if there is an error when finding the PhongCachNoiThat object.
     */
    public PhongCachNoiThat findUsingName(String name) {
        String path = String.format(BASE_ENDPOINT + "/search" + OWNER_TEMPLATE + NAME_TEMPLATE,
                userSessionService.getUsername(), Utils.encodeValue(name));
        String response = webClientService.authorizedHttpGetJson(path, userSessionService.getToken());
        if (response == null) {
            return null;
        }
        try {
            return objectMapper.readValue(response, PhongCachNoiThat.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Can't parse response from server");
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves a new PhongCachNoiThat object.
     *
     * @param phongCachNoiThat The PhongCachNoiThat object to be saved.
     * @throws RuntimeException if there is an error when saving the PhongCachNoiThat object.
     */
    public void save(PhongCachNoiThat phongCachNoiThat) {
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE, userSessionService.getUsername());
        String token = this.userSessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson(path, objectMapper.writeValueAsString(phongCachNoiThat), token);
        } catch (IOException e) {
            LOGGER.error("Error when adding new PhongCach");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing PhongCachNoiThat object.
     *
     * @param phongCachNoiThat The PhongCachNoiThat object to be updated.
     * @throws RuntimeException if there is an error when updating the PhongCachNoiThat object.
     */
    public void update(PhongCachNoiThat phongCachNoiThat) {
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE, userSessionService.getUsername());
        String token = this.userSessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson(path, objectMapper.writeValueAsString(phongCachNoiThat), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing PhongCach");
            throw new RuntimeException(e);
        }
    }

/**
 * Deletes a PhongCachNoiThat
 * */
    public void deleteById(int id) {
        String path = String.format(BASE_ENDPOINT + ID_TEMPLATE + OWNER_TEMPLATE, id, userSessionService.getUsername());
        String token = this.userSessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson(path, "", token);
    }

    public void copySampleDataFromAdmin() {
        String path = String.format(BASE_ENDPOINT + "/copySampleData");
        String token = this.userSessionService.getToken();
        this.webClientService.authorizedHttpGetJson(path, token);
    }

    public void swap(int id1, int id2) {
        String path = String.format(BASE_ENDPOINT + "/swap?id1=%d&id2=%d", id1, id2);
        String token = this.userSessionService.getToken();
        this.webClientService.authorizedHttpGetJson(path, token);
    }
}
