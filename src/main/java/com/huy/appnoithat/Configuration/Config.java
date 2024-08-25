package com.huy.appnoithat.Configuration;

import java.nio.file.Paths;

public class Config {
    public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
    public static final String CONFIG_DIRECTORY =
            Paths.get(CURRENT_DIRECTORY, "Configuration", "config.yaml").toString();
    private static final String DEFAULT_SESSION_DIRECTORY =
            Paths.get("Data", "UserSession", "session").toString();
    private static final String DEFAULT_COMPANY_INFO_DIRECTORY =
            Paths.get("Data", "PersistenceData", "info.json").toString();
    private static final String DEFAULT_NOTE_AREA_DIRECTORY =
            Paths.get("Data", "PersistenceData", "note.json").toString();
    private static final String DEFAULT_XLSX_TEMPLATE_DIRECTORY =
            Paths.get("Data", "FileExport", "XLSXTemplate", "template.xlsx").toString();
    private static final String DEFAULT_XLSX_OUTPUT_DIRECTORY =
            Paths.get("Data", "FileExport", "Output").toString();
    private static final String DEFAULT_RECENT_NT_FILE_DIRECTORY =
            Paths.get("Data", "RecentFile", "recentFile.json").toString();
    private static final String DEFAULT_TEMP_NT_FILE_DIRECTORY =
            Paths.get("Data", "Temp").toString();
    private static final String DEFAULT_VBS_SCRIPT_DIRECTORY =
            Paths.get("Data", "FileExport", "VBScript", "script.vbs").toString();
    public static String ROOT_DIRECTORY;

    public static void setupDirectory() {
        USER.SESSION_DIRECTORY = Paths.get(ROOT_DIRECTORY, DEFAULT_SESSION_DIRECTORY).toString();
        USER.COMPANY_INFO_DIRECTORY = Paths.get(ROOT_DIRECTORY, DEFAULT_COMPANY_INFO_DIRECTORY).toString();
        USER.NOTE_AREA_DIRECTORY = Paths.get(ROOT_DIRECTORY, DEFAULT_NOTE_AREA_DIRECTORY).toString();
        FILE_EXPORT.XLSX_DEFAULT_OUTPUT_DIRECTORY = Paths.get(ROOT_DIRECTORY, DEFAULT_XLSX_OUTPUT_DIRECTORY).toString();
        FILE_EXPORT.RECENT_NT_FILE_DIRECTORY = Paths.get(ROOT_DIRECTORY, DEFAULT_RECENT_NT_FILE_DIRECTORY).toString();
        FILE_EXPORT.TEMP_NT_FILE_DIRECTORY = Paths.get(ROOT_DIRECTORY, DEFAULT_TEMP_NT_FILE_DIRECTORY).toString();
        FILE_EXPORT.VBS_SCRIPT_DIRECTORY = Paths.get(ROOT_DIRECTORY, DEFAULT_VBS_SCRIPT_DIRECTORY).toString();
        FILE_EXPORT.XLSX_TEMPLATE_DIRECTORY = Paths.get(ROOT_DIRECTORY, DEFAULT_XLSX_TEMPLATE_DIRECTORY).toString();
    }

    public static class USER {
        public static String SESSION_DIRECTORY;
        public static String COMPANY_INFO_DIRECTORY;
        public static String NOTE_AREA_DIRECTORY;

    }

    public static class FILE_EXPORT {
        public static String XLSX_TEMPLATE_DIRECTORY;
        public static String XLSX_DEFAULT_OUTPUT_DIRECTORY;
        public static String RECENT_NT_FILE_DIRECTORY;
        public static String TEMP_NT_FILE_DIRECTORY;
        public static String VBS_SCRIPT_DIRECTORY;
    }

    public static class WEB_CLIENT {
        public static String BASE_URL = "http://localhost:8080";
        //        public static final String BASE_URL = "http://localhost:8085";
        public static int TIME_OUT = 10;
    }
}
