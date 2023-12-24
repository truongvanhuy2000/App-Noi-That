package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ThongSoRestService {
    private static ThongSoRestService instance;
    final static Logger LOGGER = LogManager.getLogger(VatLieuRestService.class);
    private final WebClientService webClientService;
    private static final String BASE_ENDPOINT = "/api/thongso";
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
        webClientService = new WebClientService();
    }

    /**
     * Saves a new ThongSo object.
     *
     * @param thongSo   The ThongSo object to be saved.
     * @param parentId  The ID of the parent entity associated with this ThongSo.
     * @throws RuntimeException if there is an error when saving the ThongSo object.
     */
    public void save(ThongSo thongSo, int parentId) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT)
                .addParameter("parentId", String.valueOf(parentId));
        Optional<String> response = this.webClientService.authorizedHttpPostJson(uriBuilder, thongSo, String.class);
        response.orElseThrow(() -> {
            LOGGER.error("Can't save ThongSo");
            return new RuntimeException("Can't save ThongSo");
        });
    }

    /**
     * Searches for ThongSo objects by VatLieu ID.
     *
     * @param id The ID of the VatLieu to search for associated ThongSo objects.
     * @return A list of ThongSo objects associated with the given VatLieu ID, or null if not found.
     * @throws RuntimeException if there is an error when searching for ThongSo objects.
     */
    public List<ThongSo> searchByVatLieu(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("searchByVatLieu", String.valueOf(id));
        Optional<List<ThongSo>> response = this.webClientService.authorizedHttpGetJson(uriBuilder, ThongSo.class, List.class);
        return response.orElse(null);
    }
    /**
     * Updates an existing ThongSo object.
     *
     * @param thongSo The ThongSo object to be updated.
     * @throws RuntimeException if there is an error when updating the ThongSo object.
     */
    public void update(ThongSo thongSo) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT);
        Optional<String> response = this.webClientService.authorizedHttpPutJson(uriBuilder, thongSo, String.class);
        response.orElseThrow(() -> {
            LOGGER.error("Can't update ThongSo");
            return new RuntimeException("Can't update ThongSo");
        });
    }
    public void copySampleDataFromAdmin(int parentId) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("copySampleData")
                .addParameter("parentId", String.valueOf(parentId));
        Optional<String> response = this.webClientService.authorizedHttpGetJson(uriBuilder, String.class);
    }
}
