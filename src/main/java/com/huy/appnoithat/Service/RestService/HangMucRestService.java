package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.DataModel.Entity.HangMuc;
import com.huy.appnoithat.Service.WebClient.JavaNetHttpClient;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.util.List;
import java.util.Optional;

public class HangMucRestService {
    private static HangMucRestService instance;
    final static Logger LOGGER = LogManager.getLogger(HangMucRestService.class);
    private final WebClientService webClientService;
    private static final String BASE_ENDPOINT = "/api/hangmuc";
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
        webClientService = new JavaNetHttpClient();
    }

    /**
     * Searches for HangMuc items associated with a specific NoiThat ID.
     *
     * @param id The ID of the NoiThat to search for.
     * @return A list of HangMuc items associated with the given NoiThat ID, or null if no items are found.
     */
    public List<HangMuc> searchByNoiThat(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("searchByNoiThat", String.valueOf(id));
        Optional<List<HangMuc>> response = this.webClientService.authorizedHttpGet(uriBuilder, HangMuc.class, List.class);
        return response.orElse(null);
    }

    /**
     * Saves a HangMuc object associated with a specific parent NoiThat ID.
     *
     * @param hangMuc The HangMuc object to save.
     * @param parentID The ID of the parent NoiThat.
     */
    public void save(HangMuc hangMuc, int parentID) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT)
                .addParameter("parentId", String.valueOf(parentID));
        Optional<String> response = this.webClientService.authorizedHttpPost(uriBuilder, hangMuc, String.class);
        response.orElseThrow(() -> {
            LOGGER.error("Error when saving HangMuc");
            return new RuntimeException("Error when saving HangMuc");
        });
    }

    /**
     * Updates the details of an existing HangMuc object.
     *
     * @param hangMuc The HangMuc object with updated details.
     * @throws RuntimeException if there is an error when editing the HangMuc.
     */
    public void update(HangMuc hangMuc) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT);
        Optional<String> response = this.webClientService.authorizedHttpPutJson(uriBuilder, hangMuc, String.class);
        response.orElseThrow(() -> {
            LOGGER.error("Error when editing HangMuc");
            return new RuntimeException("Error when editing HangMuc");
        });
    }

    /**
     * Deletes a HangMuc object by its ID.
     *
     * @param id The ID of the HangMuc object to be deleted.
     */
    public void deleteById(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath(String.valueOf(id));
        this.webClientService.authorizedHttpDeleteJson(uriBuilder, String.class);
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
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("searchBy")
                .addParameter("phongCachName", phongCachName)
                .addParameter("noiThatName", noiThatName);
        Optional<List<HangMuc>> response = this.webClientService.authorizedHttpGet(uriBuilder, HangMuc.class, List.class);
        return response.orElse(null);
    }
    public void copySampleDataFromAdmin(int parentId) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("copySampleData")
                .addParameter("parentId", String.valueOf(parentId));
        this.webClientService.authorizedHttpGet(uriBuilder, String.class);
    }

    public void swap(int id1, int id2) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("swap")
                .addParameter("id1", String.valueOf(id1))
                .addParameter("id2", String.valueOf(id2));
        this.webClientService.authorizedHttpGet(uriBuilder, String.class);
    }
}
