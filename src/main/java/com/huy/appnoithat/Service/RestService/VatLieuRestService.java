package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class VatLieuRestService {
    private static VatLieuRestService instance;
    final static Logger LOGGER = LogManager.getLogger(VatLieuRestService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService userSessionService;
    private static final String BASE_ENDPOINT = "/api/vatlieu";
    private static final String ID_TEMPLATE = "/%d";
    private static final String OWNER_TEMPLATE = "?owner=%s";
    private static final String PARENT_ID_TEMPLATE = "&parentId=%d";
    public static synchronized VatLieuRestService getInstance() {
        if (instance == null) {
            instance = new VatLieuRestService();
        }
        return instance;
    }

    /**
     * Constructor for VatLieuRestService.
     */
    private VatLieuRestService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    /**
     * Searches for VatLieu objects by HangMuc ID.
     *
     * @param id The ID of the HangMuc to search for associated VatLieu objects.
     * @return A list of VatLieu objects associated with the given HangMuc ID, or null if not found.
     * @throws RuntimeException if there is an error when searching for VatLieu objects.
     */
    public List<VatLieu> searchByHangMuc(int id) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + "/searchByHangMuc" + ID_TEMPLATE + OWNER_TEMPLATE, id, userSessionService.getUsername());
        String response = this.webClientService.authorizedHttpGetJson(path, token);
        if (response == null) {
            return null;
        }
        try {
            // 2. convert JSON array to List of objects
            return objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, VatLieu.class));
        } catch (IOException e) {
            LOGGER.error("Error when finding vat lieu");
            throw new RuntimeException(e);
        }
    }
    /**
     * Saves a new VatLieu object.
     *
     * @param vatLieu  The VatLieu object to be saved.
     * @param parentID The ID of the parent entity associated with this VatLieu.
     * @throws RuntimeException if there is an error when saving the VatLieu object.
     */
    public void save(VatLieu vatLieu, int parentID) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE + PARENT_ID_TEMPLATE, userSessionService.getUsername(), parentID);
        try {
            this.webClientService.authorizedHttpPostJson(path, objectMapper.writeValueAsString(vatLieu), token);
        } catch (IOException e) {
            LOGGER.error("Error when adding new VatLieu");
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing VatLieu object.
     *
     * @param vatLieu The VatLieu object to be updated.
     * @throws RuntimeException if there is an error when updating the VatLieu object.
     */
    public void update(VatLieu vatLieu) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE, userSessionService.getUsername());
        try {
            this.webClientService.authorizedHttpPutJson(path, objectMapper.writeValueAsString(vatLieu), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing VatLieu");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a VatLieu object by its ID.
     *
     * @param id The ID of the VatLieu object to be deleted.
     * @throws RuntimeException if there is an error when deleting the VatLieu object.
     */
    public void deleteById(int id) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + ID_TEMPLATE + OWNER_TEMPLATE, id, userSessionService.getUsername());
        this.webClientService.authorizedHttpDeleteJson(path, "", token);
    }

    /**
     * Searches for VatLieu objects by PhongCachName, NoiThatName, and HangMucName.
     *
     * @param phongCachName The name of the PhongCach associated with the VatLieu.
     * @param noiThatName   The name of the NoiThat associated with the VatLieu.
     * @param hangMucName   The name of the HangMuc associated with the VatLieu.
     * @return A list of VatLieu objects matching the search criteria, or null if not found.
     * @throws RuntimeException if there is an error when searching for VatLieu objects.
     */
    public List<VatLieu> searchBy(String phongCachName, String noiThatName, String hangMucName) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + "/searchBy" + OWNER_TEMPLATE + "&phongCachName=%s&noiThatName=%s&hangMucName=%s",
                userSessionService.getUsername(), phongCachName, noiThatName, hangMucName);
        String response = this.webClientService.authorizedHttpGetJson(path, token);
        if (response == null) {
            return null;
        }
        try {
            // 2. convert JSON array to List of objects
            return objectMapper.readValue(response, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, VatLieu.class));
        } catch (IOException e) {
            LOGGER.error("Error when finding vat lieu");
            throw new RuntimeException(e);
        }
    }
    public void copySampleDataFromAdmin(int parentId) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + "/copySampleData" + "?parentId=%d", parentId);
        this.webClientService.authorizedHttpGetJson(path, token);
    }
}
