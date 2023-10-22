package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class HangMucRestService {
    private static HangMucRestService instance;
    final static Logger LOGGER = LogManager.getLogger(HangMucRestService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService userSessionService;
    private static final String BASE_ENDPOINT = "/api/hangmuc";
    private static final String ID_TEMPLATE = "/%d";
    private static final String OWNER_TEMPLATE = "?owner=%s";
    private static final String PARENT_ID_TEMPLATE = "&parentId=%d";
    public static synchronized HangMucRestService getInstance() {
        if (instance == null) {
            instance = new HangMucRestService();
        }
        return instance;
    }

    /**
     * Constructs a new instance of HangMucRestService with the necessary dependencies.
     */
    private HangMucRestService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    /**
     * Searches for HangMuc items associated with a specific NoiThat ID.
     *
     * @param id The ID of the NoiThat to search for.
     * @return A list of HangMuc items associated with the given NoiThat ID, or null if no items are found.
     */
    public List<HangMuc> searchByNoiThat(int id) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + "/searchByNoiThat" + ID_TEMPLATE + OWNER_TEMPLATE, id, userSessionService.getUsername());
        String response2 = this.webClientService.authorizedHttpGetJson(path, token);
        if (response2 == null) {
            return null;
        }
        try {
            // Convert JSON array to List of HangMuc objects
            return objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, HangMuc.class));
        } catch (IOException e) {
            LOGGER.error("Error when finding hang muc");
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves a HangMuc object associated with a specific parent NoiThat ID.
     *
     * @param hangMuc The HangMuc object to save.
     * @param parentID The ID of the parent NoiThat.
     */
    public void save(HangMuc hangMuc, int parentID) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE + PARENT_ID_TEMPLATE, userSessionService.getUsername(), parentID);
        try {
            this.webClientService.authorizedHttpPostJson(path, objectMapper.writeValueAsString(hangMuc), token);
        } catch (IOException e) {
            LOGGER.error("Error when adding new HangMuc");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the details of an existing HangMuc object.
     *
     * @param hangMuc The HangMuc object with updated details.
     * @throws RuntimeException if there is an error when editing the HangMuc.
     */
    public void update(HangMuc hangMuc) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE, userSessionService.getUsername());
        try {
            this.webClientService.authorizedHttpPutJson(path, objectMapper.writeValueAsString(hangMuc), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing HangMuc");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a HangMuc object by its ID.
     *
     * @param id The ID of the HangMuc object to be deleted.
     */
    public void deleteById(int id) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + ID_TEMPLATE + OWNER_TEMPLATE, id, userSessionService.getUsername());
        this.webClientService.authorizedHttpDeleteJson(path, "", token);
    }

    /**
     * Searches for HangMuc objects based on the provided PhongCach and NoiThat names.
     *
     * @param phongCachName The name of the PhongCach to search for.
     * @param noiThatName The name of the NoiThat to search for.
     * @return A list of HangMuc objects matching the search criteria.
     * @throws RuntimeException if there is an error when searching for HangMuc objects.
     */
    public List<HangMuc> searchBy(String phongCachName, String noiThatName) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + "/searchBy" + OWNER_TEMPLATE + "&phongCachName=%s&noiThatName=%s", userSessionService.getUsername(), phongCachName, noiThatName);
        String response2 = this.webClientService.authorizedHttpGetJson(path, token);
        if (response2 == null) {
            return null;
        }
        try {
            // 2. convert JSON array to List of objects
            return objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, HangMuc.class));
        } catch (IOException e) {
            LOGGER.error("Error when searching hang muc");
            throw new RuntimeException(e);
        }
    }
    public void copySampleDataFromAdmin(int parentId) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + "/copySampleData" + "?parentId=%d", parentId);
        this.webClientService.authorizedHttpGetJson(path, token);
    }
}
