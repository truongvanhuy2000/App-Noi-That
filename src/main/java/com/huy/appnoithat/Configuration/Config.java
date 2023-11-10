package com.huy.appnoithat.Configuration;

import java.nio.file.Paths;

public class Config {
    private static final String CURRENT_DIRECTORY = System.getProperty("user.dir");

    public static class USER {
        public static final String SESSION_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "UserSession", "session").toString();
        public static final String COMPANY_INFO_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "PersistenceData", "info.json").toString();
        public static final String NOTE_AREA_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "PersistenceData", "note.json").toString();
        public static final String CONFIG_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "Configuration", "config.yaml").toString();
    }

    public static class FILE_EXPORT {
        public static final String XLSX_TEMPLATE_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "FileExport", "XLSXTemplate", "template.xlsx").toString();
        public static final String XLSX_DEFAULT_OUTPUT_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "FileExport", "Output").toString();
        public static final String RECENT_NT_FILE_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "RecentFile", "recentFile.json").toString();
        public static final String TEMP_NT_FILE_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "Temp").toString();
    }
    public static class WEB_CLIENT {
        public static String BASE_URL = "http://localhost:8080";
//        public static final String BASE_URL = "http://localhost:8085";
        public static int TIME_OUT = 10;
    }
}
