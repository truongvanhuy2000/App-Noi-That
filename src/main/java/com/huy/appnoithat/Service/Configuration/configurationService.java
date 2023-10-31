package com.huy.appnoithat.Service.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.DataModel.Yaml.Configuration;
import com.huy.appnoithat.DataModel.Yaml.Webserver;
import com.huy.appnoithat.Service.DatabaseModify.DatabaseModifyVatlieuService;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class configurationService {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseModifyVatlieuService.class);
    public static void readConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        try {
            Configuration configuration = objectMapper.readValue(new File(Config.USER.CONFIG_DIRECTORY), Configuration.class);
            setWebServer(configuration.getWebserver());
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setWebServer(Webserver webServer) {
        Config.WEB_CLIENT.BASE_URL = webServer.getHost();
        Config.WEB_CLIENT.TIME_OUT = webServer.getTimeout();
    }
}
