package com.huy.appnoithat.Module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.huy.appnoithat.Module.HttpClientModule.ApacheHttp;
import com.huy.appnoithat.Module.HttpClientModule.JavaNet;
import com.huy.appnoithat.Service.RestService.*;
import com.huy.appnoithat.Service.WebClient.WebClientService;

public class RestModule extends AbstractModule {
    @Provides
    AccountRestService accountRestService(@JavaNet WebClientService webClientService) {
        return new AccountRestService(webClientService);
    }

    @Provides
    BangNoiThatRestService bangNoiThatRestService(@JavaNet WebClientService webClientService) {
        return new BangNoiThatRestService(webClientService);
    }

    @Provides
    HangMucRestService hangMucRestService(@JavaNet WebClientService webClientService) {
        return new HangMucRestService(webClientService);
    }

    @Provides
    LapBaoGiaRestService lapBaoGiaRestService(@JavaNet WebClientService webClientService) {
        return new LapBaoGiaRestService(webClientService);
    }

    @Provides
    NoiThatRestService noiThatRestService(@JavaNet WebClientService webClientService) {
        return new NoiThatRestService(webClientService);
    }

    @Provides
    PhongCachRestService phongCachRestService(@JavaNet WebClientService webClientService) {
        return new PhongCachRestService(webClientService);
    }

    @Provides
    PricingModelRestService pricingModelRestService(@JavaNet WebClientService webClientService) {
        return new PricingModelRestService(webClientService);
    }

    @Provides
    ThongSoRestService thongSoRestService(@JavaNet WebClientService webClientService) {
        return new ThongSoRestService(webClientService);
    }

    @Provides
    VatLieuRestService vatLieuRestService(@JavaNet WebClientService webClientService) {
        return new VatLieuRestService(webClientService);
    }

    @Provides
    FileStorageRestService fileStorageRestService(@ApacheHttp WebClientService webClientService) {
        return new FileStorageRestService(webClientService);
    }

    @Provides
    TokenRestService tokenRestService(@ApacheHttp WebClientService webClientService) {
        return new TokenRestService(webClientService);
    }
}
