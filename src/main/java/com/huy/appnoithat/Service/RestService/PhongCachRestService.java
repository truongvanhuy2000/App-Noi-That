package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.DataModel.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PhongCachRestService {
    final static Logger LOGGER = LogManager.getLogger(PhongCachRestService.class);
    private final WebClientService webClientService;
    private static final String BASE_ENDPOINT = "/api/phongcach";

    /**
     * Retrieves a list of all PhongCachNoiThat objects associated with the current user.
     *
     * @return A list of PhongCachNoiThat objects.
     * @throws RuntimeException if there is an error when retrieving PhongCachNoiThat objects.
     */
    public List<PhongCachNoiThat> findAll() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT);
        Optional<List<PhongCachNoiThat>> response = this.webClientService.authorizedHttpGet(uriBuilder, PhongCachNoiThat.class, List.class);
        if (response.isEmpty()) {
            LOGGER.error("Not found any phong cach");
        }
        return response.orElse(new ArrayList<>());
    }

    /**
     * Finds a PhongCachNoiThat object by its ID.
     *
     * @param id The ID of the PhongCachNoiThat to find.
     * @return The PhongCachNoiThat object with the specified ID, or null if not found.
     * @throws RuntimeException if there is an error when finding the PhongCachNoiThat object.
     */
    public PhongCachNoiThat findById(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath(String.valueOf(id));
        Optional<PhongCachNoiThat> response = this.webClientService.authorizedHttpGet(uriBuilder, PhongCachNoiThat.class);
        return response.orElse(null);
    }

    /**
     * Finds a PhongCachNoiThat object by its name.
     *
     * @param name The name of the PhongCachNoiThat to find.
     * @return The PhongCachNoiThat object with the specified name, or null if not found.
     * @throws RuntimeException if there is an error when finding the PhongCachNoiThat object.
     */
    public PhongCachNoiThat findUsingName(String name) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("search").addParameter("name", name);
        Optional<PhongCachNoiThat> response = this.webClientService.authorizedHttpGet(uriBuilder, PhongCachNoiThat.class);
        return response.orElse(null);
    }

    /**
     * Saves a new PhongCachNoiThat object.
     *
     * @param phongCachNoiThat The PhongCachNoiThat object to be saved.
     * @throws RuntimeException if there is an error when saving the PhongCachNoiThat object.
     */
    public void save(PhongCachNoiThat phongCachNoiThat) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT);
        Optional<String> response = this.webClientService.authorizedHttpPost(uriBuilder, phongCachNoiThat, String.class);
        response.orElseThrow(() -> {
            LOGGER.error("Can't save PhongCach");
            return new RuntimeException("Can't save PhongCach");
        });
    }

    /**
     * Updates an existing PhongCachNoiThat object.
     *
     * @param phongCachNoiThat The PhongCachNoiThat object to be updated.
     * @throws RuntimeException if there is an error when updating the PhongCachNoiThat object.
     */
    public void update(PhongCachNoiThat phongCachNoiThat) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT);
        Optional<String> response = this.webClientService.authorizedHttpPutJson(uriBuilder, phongCachNoiThat, String.class);
        response.orElseThrow(() -> {
            LOGGER.error("Can't update PhongCach");
            return new RuntimeException("Can't update PhongCach");
        });
    }

    /**
     * Deletes a PhongCachNoiThat
     */
    public void deleteById(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath(String.valueOf(id));
        this.webClientService.authorizedHttpDeleteJson(uriBuilder, String.class);
    }

    public void copySampleDataFromAdmin() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("copySampleData");
        this.webClientService.authorizedHttpGet(uriBuilder, String.class);
    }

    public void swap(int id1, int id2) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT)
                .addPath("swap")
                .addParameter("id1", String.valueOf(id1))
                .addParameter("id2", String.valueOf(id2));
        this.webClientService.authorizedHttpGet(uriBuilder, String.class);
    }
}
