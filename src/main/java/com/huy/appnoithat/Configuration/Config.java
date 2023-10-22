package com.huy.appnoithat.Configuration;

import java.nio.file.Paths;

public class Config {
    private static final String CURRENT_DIRECTORY = System.getProperty("user.dir");

    public static class USER {
        public static final String SESSION_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "UserSession", "session").toString();
        public static final String COMPANY_INFO_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "CompanyInfo", "info.json").toString();
    }

    public static class FILE_EXPORT {
        public static final String XLSX_TEMPLATE_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "FileExport", "XLSXTemplate", "template.xlsx").toString();
        public static final String XLSX_DEFAULT_OUTPUT_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "FileExport", "Output").toString();
        public static final String RECENT_NT_FILE_DIRECTORY =
                Paths.get(CURRENT_DIRECTORY, "Data", "RecentFile", "recentFile.json").toString();
    }
    public static class WEB_CLIENT {
//        public static final String BASE_URL = "http://103.238.234.22:8080";
        public static final String BASE_URL = "http://localhost:8080";
        public static final int TIME_OUT = 10;
    }
}
