package com.huy.appnoithat.Service.RestService;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.DataModel.PricingModelDTO;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.util.Optional;

@RequiredArgsConstructor
public class PricingModelRestService {
    final static Logger LOGGER = LogManager.getLogger(PricingModelRestService.class);
    private final WebClientService webClientService;
    private static final String BASE_ENDPOINT = "/api/pricingModel";

    public PricingModelDTO getPricingModel() {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT);
        Optional<PricingModelDTO> response = this.webClientService.unauthorizedHttpGetJson(uriBuilder, PricingModelDTO.class);
        return response.orElse(null);
    }

    public void setPricingModel(PricingModelDTO pricingModel) {
        URIBuilder uriBuilder = URIBuilder.empty().addRawPath(BASE_ENDPOINT);
        Optional<String> response = this.webClientService.authorizedHttpPost(uriBuilder, pricingModel, String.class);
        if (response.isEmpty()) {
            LOGGER.error("Can't set pricing model");
            PopupUtils.throwErrorNotification("Không thể thiết lập mô hình thanh toán");
        } else {
            PopupUtils.throwSuccessNotification("Thành công");
        }
    }
}
