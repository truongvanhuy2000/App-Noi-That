package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.DataModel.LapBaoGiaInfo;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class LapBaoGiaRestService {
    final static Logger LOGGER = LogManager.getLogger(LapBaoGiaRestService.class);
    private static final String BASE_ENDPOINT = "/api/lapBaoGiaInfo";
    private final WebClientService webClientService;
    public LapBaoGiaRestService() {
        webClientService = new WebClientService();
    }

    public ThongTinCongTy getThongTinCongTy() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("thongTinCongTy");
        Optional<ThongTinCongTy> response = this.webClientService.authorizedHttpGetJson(uriBuilder, ThongTinCongTy.class);
        return response.orElse(null);
    }

    public String getNote() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("noteArea");
        Optional<String> response = this.webClientService.authorizedHttpGetJson(uriBuilder, String.class);
        return response.orElse(null);
    }

    public void saveThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("thongTinCongTy");
        Optional<String> response = this.webClientService.authorizedHttpPostJson(uriBuilder, thongTinCongTy, String.class);
        if (response.isEmpty()) {
            LOGGER.error("Error when save ThongTinCongTy");
        }
    }
    public void saveNote(String note) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("noteArea");
        Optional<String> response = this.webClientService.authorizedHttpPostJson(uriBuilder, note, String.class);
        if (response.isEmpty()) {
            LOGGER.error("Error when save Note");
        }
    }

    public boolean checkInfoModification(Date date) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT)
                .addPath("checkModification")
                .addParameter("date", DateFormat.getInstance().format(date));
        Optional<Map> response = this.webClientService.authorizedHttpGetJson(uriBuilder, Map.class);
        if (response.isEmpty()) {
            LOGGER.error("Error when check info modification");
            return false;
        }
        return (boolean) response.get().get("modified");
    }
}
