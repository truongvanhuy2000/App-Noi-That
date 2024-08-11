package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class LapBaoGiaRestService {
    final static Logger LOGGER = LogManager.getLogger(LapBaoGiaRestService.class);
    private static final String BASE_ENDPOINT = "/api/lapBaoGiaInfo";
    private final WebClientService webClientService;

    public ThongTinCongTy getThongTinCongTy() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("thongTinCongTy");
        Optional<ThongTinCongTy> response = this.webClientService.authorizedHttpGet(uriBuilder, ThongTinCongTy.class);
        return response.orElse(null);
    }

    public String getNote() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("noteArea");
        Optional<String> response = this.webClientService.authorizedHttpGet(uriBuilder, String.class);
        return response.orElse(null);
    }

    public void saveThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("thongTinCongTy");
        Optional<String> response = this.webClientService.authorizedHttpPost(uriBuilder, thongTinCongTy, String.class);
        if (response.isEmpty()) {
            LOGGER.error("Error when save ThongTinCongTy");
        } else {
            PopupUtils.throwSuccessNotification("Lưu thông tin công ty thành công");
        }
    }
    public void saveNote(String note) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT).addPath("noteArea");
        Optional<String> response = this.webClientService.authorizedHttpPost(uriBuilder, note, String.class);
        if (response.isEmpty()) {
            LOGGER.error("Error when save Note");
        } else {
            PopupUtils.throwSuccessNotification("Lưu ghi chú thành công");
        }
    }

    public boolean checkInfoModification(Date date) {
        URIBuilder uriBuilder = URIBuilder.empty()
                .addRawPath(BASE_ENDPOINT)
                .addPath("checkModification")
                .addParameter("date", DateFormat.getInstance().format(date));
        Optional<Map> response = this.webClientService.authorizedHttpGet(uriBuilder, Map.class);
        if (response.isEmpty()) {
            LOGGER.error("Error when check info modification");
            return false;
        }
        return (boolean) response.get().get("modified");
    }
}
