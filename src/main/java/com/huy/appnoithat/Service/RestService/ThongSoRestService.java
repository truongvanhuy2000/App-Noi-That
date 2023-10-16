package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ThongSoRestService {
    private static ThongSoRestService instance;
    final static Logger LOGGER = LogManager.getLogger(VatLieuRestService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService userSessionService;
    private static final String BASE_ENDPOINT = "/api/thongso";
    private static final String ID_TEMPLATE = "/%d";
    private static final String OWNER_TEMPLATE = "?owner=%s";
    private static final String PARENT_ID_TEMPLATE = "&parentId=%d";
    public static synchronized ThongSoRestService getInstance() {
        if (instance == null) {
            instance = new ThongSoRestService();
        }
        return instance;
    }

    /**
     * Constructor for ThongSoRestService.
     */
    private ThongSoRestService() {
        webClientService = new WebClientServiceImpl();
        userSessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    /**
     * Saves a new ThongSo object.
     *
     * @param thongSo   The ThongSo object to be saved.
     * @param parentId  The ID of the parent entity associated with this ThongSo.
     * @throws RuntimeException if there is an error when saving the ThongSo object.
     */
    public void save(ThongSo thongSo, int parentId) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE + PARENT_ID_TEMPLATE, userSessionService.getUsername(), parentId);
        try {
            this.webClientService.authorizedHttpPostJson(path, objectMapper.writeValueAsString(thongSo), token);
        } catch (IOException e) {
            LOGGER.error("Error when saving ThongSo");
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for ThongSo objects by VatLieu ID.
     *
     * @param id The ID of the VatLieu to search for associated ThongSo objects.
     * @return A list of ThongSo objects associated with the given VatLieu ID, or null if not found.
     * @throws RuntimeException if there is an error when searching for ThongSo objects.
     */
    public List<ThongSo> searchByVatLieu(int id) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + "/searchByVatlieu" + ID_TEMPLATE + OWNER_TEMPLATE, id, userSessionService.getUsername());
        String response2 = this.webClientService.authorizedHttpGetJson(path, token);
        if (response2 == null) {
            return null;
        }
        try {
            // 2. convert JSON array to List of objects
            return objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, ThongSo.class));
        } catch (IOException e) {
            LOGGER.error("Error when finding thong so");
            throw new RuntimeException(e);
        }
    }
    /**
     * Updates an existing ThongSo object.
     *
     * @param thongSo The ThongSo object to be updated.
     * @throws RuntimeException if there is an error when updating the ThongSo object.
     */
    public void update(ThongSo thongSo) {
        String token = this.userSessionService.getToken();
        String path = String.format(BASE_ENDPOINT + OWNER_TEMPLATE, userSessionService.getUsername());
        try {
            this.webClientService.authorizedHttpPutJson(path, objectMapper.writeValueAsString(thongSo), token);
        } catch (IOException e) {
            LOGGER.error("Error when editing ThongSo");
            throw new RuntimeException(e);
        }
    }
}
