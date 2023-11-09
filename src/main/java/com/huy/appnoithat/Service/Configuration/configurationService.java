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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class configurationService {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseModifyVatlieuService.class);
    public static void readConfiguration() {
        checkConfiguration();
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
    private static void checkConfiguration() {
        try {
            File configDIr = new File(Config.USER.SESSION_DIRECTORY);
            if (!configDIr.exists()) {
                Files.createDirectories(Paths.get(Config.USER.SESSION_DIRECTORY));
            }
            File companyInfo = new File(Config.USER.COMPANY_INFO_DIRECTORY);
            if (!companyInfo.exists()) {
                Path path = Paths.get(Config.USER.COMPANY_INFO_DIRECTORY);
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
            File noteArea = new File(Config.USER.NOTE_AREA_DIRECTORY);
            if (!noteArea.exists()) {
                Path path = Paths.get(Config.USER.NOTE_AREA_DIRECTORY);
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
            File config = new File(Config.USER.CONFIG_DIRECTORY);
            if (!config.exists()) {
                Path path = Paths.get(Config.USER.CONFIG_DIRECTORY);
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
            File xlsxTemplate = new File(Config.FILE_EXPORT.XLSX_TEMPLATE_DIRECTORY);
            if (!xlsxTemplate.exists()) {
                throw new RuntimeException("XLSX template file not found");
            }
            File xlsxDefaultOutput = new File(Config.FILE_EXPORT.XLSX_DEFAULT_OUTPUT_DIRECTORY);
            if (!xlsxDefaultOutput.exists()) {
                Files.createDirectories(Paths.get(Config.FILE_EXPORT.XLSX_DEFAULT_OUTPUT_DIRECTORY));
            }
            File recentFile = new File(Config.FILE_EXPORT.RECENT_NT_FILE_DIRECTORY);
            if (!recentFile.exists()) {
                Path path = Paths.get(Config.FILE_EXPORT.RECENT_NT_FILE_DIRECTORY);
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
            File tempFile = new File(Config.FILE_EXPORT.TEMP_NT_FILE_DIRECTORY);
            if (!tempFile.exists()) {
                Files.createDirectories(Paths.get(Config.FILE_EXPORT.TEMP_NT_FILE_DIRECTORY));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
