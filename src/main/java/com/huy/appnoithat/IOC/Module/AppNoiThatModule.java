package com.huy.appnoithat.IOC.Module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;


public class AppNoiThatModule extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();
        install(new ControllerModule());
        install(new ServiceModule());
        install(new SceneModule());
        install(new RestModule());
        install(new HttpClientModule());
        install(new SessionModule());
    }

    @Provides
    ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build();
    }
}
