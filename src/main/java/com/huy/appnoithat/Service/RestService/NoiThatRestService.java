package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoiThatRestService {
    private static NoiThatRestService instance;
    final static Logger LOGGER = LogManager.getLogger(NoiThatRestService.class);
    private final WebClientService webClientService;
    private static final String BASE_ENDPOINT = "/api/noithat";
    public static synchronized NoiThatRestService getInstance() {
        if (instance == null) {
            instance = new NoiThatRestService();
        }
        return instance;
    }
    private NoiThatRestService() {
        webClientService = new WebClientService();
    }

    /**
     * Searches for NoiThat objects based on the provided PhongCach ID.
     *
     * @param id The ID of the PhongCach to search for.
     * @return A list of NoiThat objects associated with the given PhongCach ID.
     * @throws RuntimeException if there is an error when searching for NoiThat objects.
     */
    public List<NoiThat> searchByPhongCach(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("searchByPhongCach", String.valueOf(id));
        Optional<List<NoiThat>> response = this.webClientService.authorizedHttpGetJson(uriBuilder, NoiThat.class, List.class);
        return response.orElse(new ArrayList<>());
    }

    /**
     * Saves a new NoiThat object with the specified parent ID.
     *
     * @param noiThat The NoiThat object to be saved.
     * @param parentID The ID of the parent object.
     * @throws RuntimeException if there is an error when saving the NoiThat object.
     */
    public void save(NoiThat noiThat, int parentID) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addParameter("parentId", String.valueOf(parentID));
        Optional<String> response = this.webClientService.authorizedHttpPostJson(uriBuilder, noiThat, String.class);
        response.orElseThrow(() -> {
            LOGGER.error("Can't save NoiThat");
            return new RuntimeException("Can't save NoiThat");
        });
    }

    /**
     * Updates an existing NoiThat object.
     *
     * @param noiThat The NoiThat object to be updated.
     * @throws RuntimeException if there is an error when updating the NoiThat object.
     */
    public void update(NoiThat noiThat) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT);
        Optional<String> response = this.webClientService.authorizedHttpPutJson(uriBuilder, noiThat, String.class);
        response.orElseThrow(() -> {
            LOGGER.error("Can't update NoiThat");
            return new RuntimeException("Can't update NoiThat");
        });
    }

    /**
     * Deletes a NoiThat object by its ID.
     *
     * @param id The ID of the NoiThat object to be deleted.
     * @throws RuntimeException if there is an error when deleting the NoiThat object.
     */
    public void deleteById(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath(String.valueOf(id));
        this.webClientService.authorizedHttpDeleteJson(uriBuilder, String.class);
    }

    /**
     * Searches for NoiThat objects based on the provided PhongCach name.
     *
     * @param phongCachName The name of the PhongCach to search for.
     * @return A list of NoiThat objects matching the search criteria.
     * @throws RuntimeException if there is an error when searching for NoiThat objects.
     */
    public List<NoiThat> searchBy(String phongCachName) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("searchBy")
                .addParameter("phongCachName", phongCachName);
        Optional<List<NoiThat>> response = this.webClientService.authorizedHttpGetJson(uriBuilder, NoiThat.class, List.class);
        return response.orElse(new ArrayList<>());
    }
    public void copySampleDataFromAdmin(int parentId) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("copySampleData")
                .addParameter("parentId", String.valueOf(parentId));
        this.webClientService.authorizedHttpGetJson(uriBuilder, String.class);
    }

    public void swap(int id1, int id2) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("swap")
                .addParameter("id1", String.valueOf(id1))
                .addParameter("id2", String.valueOf(id2));
        this.webClientService.authorizedHttpGetJson(uriBuilder, String.class);
    }
}
