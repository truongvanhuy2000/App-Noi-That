package com.huy.appnoithat.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.huy.appnoithat.DataModel.Yaml.Configuration;
import com.huy.appnoithat.DataModel.Yaml.PathConfig;
import com.huy.appnoithat.DataModel.Yaml.Webserver;
import com.huy.appnoithat.Service.DatabaseModify.DatabaseModifyVatlieuService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigHandler {
    private final Logger LOGGER = LogManager.getLogger(DatabaseModifyVatlieuService.class);
    private static ConfigHandler instance;
    private boolean isInDevelopment = false;

    public static synchronized ConfigHandler getInstance() {
        if (instance == null) {
            instance = new ConfigHandler();
        }
        return instance;
    }

    public ConfigHandler() {
        if (System.getenv().get("APPNOITHAT_IS_DEVELOPMENT") != null) {
            isInDevelopment = true;
        }
    }

    public void readConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        try {

            Configuration configuration = objectMapper.readValue(new File(Config.CONFIG_DIRECTORY), Configuration.class);
            readWebserverConfig(configuration.getWebserver());
            readDirectoryConfig(configuration.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readWebserverConfig(Webserver webServer) {
        Config.WEB_CLIENT.BASE_URL = webServer.getHost();
        Config.WEB_CLIENT.TIME_OUT = webServer.getTimeout();
    }

    private void readDirectoryConfig(PathConfig pathConfig) {
        if (isInDevelopment) {
            Config.ROOT_DIRECTORY = Config.CURRENT_DIRECTORY;
        } else {
            Config.ROOT_DIRECTORY = pathConfig.getProductionRoot();
        }
        Config.setupDirectory();
        try {
            File configDIr = new File(Config.USER.SESSION_DIRECTORY);
            if (!configDIr.exists()) {
                Path path = Paths.get(Config.USER.SESSION_DIRECTORY);
                Files.createDirectories(path.getParent());
                Files.createFile(path);
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

            File vbScript = new File(Config.FILE_EXPORT.VBS_SCRIPT_DIRECTORY);
            if (!vbScript.exists()) {
                throw new RuntimeException("VBScript file not found");
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
