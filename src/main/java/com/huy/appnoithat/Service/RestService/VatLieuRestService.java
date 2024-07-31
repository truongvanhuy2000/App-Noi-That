package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.DataModel.Entity.VatLieu;
import com.huy.appnoithat.Service.WebClient.JavaNetHttpClient;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.util.List;

public class VatLieuRestService {
    private static VatLieuRestService instance;
    final static Logger LOGGER = LogManager.getLogger(VatLieuRestService.class);
    private final WebClientService webClientService;
    private static final String BASE_ENDPOINT = "/api/vatlieu";
    /**
     * Constructor for VatLieuRestService.
     */
    public VatLieuRestService() {
        webClientService = JavaNetHttpClient.getInstance();
    }

    /**
     * Searches for VatLieu objects by HangMuc ID.
     *
     * @param id The ID of the HangMuc to search for associated VatLieu objects.
     * @return A list of VatLieu objects associated with the given HangMuc ID, or null if not found.
     * @throws RuntimeException if there is an error when searching for VatLieu objects.
     */
    public List<VatLieu> searchByHangMuc(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("searchByHangMuc", String.valueOf(id));
        return this.webClientService.authorizedHttpGet(uriBuilder, VatLieu.class, List.class).orElse(null);
    }
    /**
     * Saves a new VatLieu object.
     *
     * @param vatLieu  The VatLieu object to be saved.
     * @param parentID The ID of the parent entity associated with this VatLieu.
     * @throws RuntimeException if there is an error when saving the VatLieu object.
     */
    public void save(VatLieu vatLieu, int parentID) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT)
                .addParameter("parentId", String.valueOf(parentID));
        if (this.webClientService.authorizedHttpPost(uriBuilder, vatLieu, String.class).isEmpty()) {
            LOGGER.error("Can't save VatLieu");
            throw new RuntimeException("Can't save VatLieu");
        }
    }

    /**
     * Updates an existing VatLieu object.
     *
     * @param vatLieu The VatLieu object to be updated.
     * @throws RuntimeException if there is an error when updating the VatLieu object.
     */
    public void update(VatLieu vatLieu) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT);
        if (this.webClientService.authorizedHttpPutJson(uriBuilder, vatLieu, String.class).isEmpty()) {
            LOGGER.error("Can't update VatLieu");
            throw new RuntimeException("Can't update VatLieu");
        }
    }

    /**
     * Deletes a VatLieu object by its ID.
     *
     * @param id The ID of the VatLieu object to be deleted.
     * @throws RuntimeException if there is an error when deleting the VatLieu object.
     */
    public void deleteById(int id) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath(String.valueOf(id));
        if (this.webClientService.authorizedHttpDeleteJson(uriBuilder, String.class).isEmpty()) {
            LOGGER.error("Can't delete VatLieu");
            throw new RuntimeException("Can't delete VatLieu");
        }
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
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("searchBy")
                .addParameter("phongCachName", phongCachName)
                .addParameter("noiThatName", noiThatName)
                .addParameter("hangMucName", hangMucName);
        return this.webClientService.authorizedHttpGet(uriBuilder, VatLieu.class, List.class).orElse(null);
    }
    public void copySampleDataFromAdmin(int parentId) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("copySampleData")
                .addParameter("parentId", String.valueOf(parentId));
        if (this.webClientService.authorizedHttpGet(uriBuilder, String.class).isEmpty()) {
            LOGGER.error("Can't copy sample data");
            throw new RuntimeException("Can't copy sample data");
        }
    }

    public void swap(int id1, int id2) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT).addPath("swap")
                .addParameter("id1", String.valueOf(id1))
                .addParameter("id2", String.valueOf(id2));
        if (this.webClientService.authorizedHttpGet(uriBuilder, String.class).isEmpty()) {
            LOGGER.error("Can't swap VatLieu");
            throw new RuntimeException("Can't swap VatLieu");
        }
    }
}
