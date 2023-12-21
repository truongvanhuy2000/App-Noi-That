package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.DataModel.LapBaoGiaInfo;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LapBaoGiaRestService {
    final static Logger LOGGER = LogManager.getLogger(LapBaoGiaRestService.class);
    private static final String BASE_ENDPOINT = "/api/lapBaoGiaInfo";
    private final WebClientService webClientService;
    private static LapBaoGiaRestService instance;
    private final ObjectMapper objectMapper;
    public static synchronized LapBaoGiaRestService getInstance() {
        if (instance == null) {
            instance = new LapBaoGiaRestService();
        }
        return instance;
    }
    private LapBaoGiaRestService() {
        webClientService = new WebClientService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
    public LapBaoGiaInfo getLapBaoGiaInfo() {
        String response = webClientService.authorizedHttpGetJson(BASE_ENDPOINT);
        if (response == null) {
            return null;
        }
        try {
            return objectMapper.readValue(response, LapBaoGiaInfo.class);
        } catch (Exception e) {
            LOGGER.error("Error when get LapBaoGiaInfo");
            return null;
        }
    }

    public void saveThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        try {
            String json = objectMapper.writeValueAsString(thongTinCongTy);
            webClientService.authorizedHttpPostJson(BASE_ENDPOINT + "/thongTinCongTy", json);
        } catch (Exception e) {
            LOGGER.error("Error when save LapBaoGiaInfo");
        }
    }
    public void saveNote(String note) {
        try {
            String json = objectMapper.writeValueAsString(note);
            webClientService.authorizedHttpPostJson(BASE_ENDPOINT + "/noteArea", json);
        } catch (Exception e) {
            LOGGER.error("Error when save LapBaoGiaInfo");
        }
    }
}
