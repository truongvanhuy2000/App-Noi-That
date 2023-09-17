package com.huy.appnoithat.Configuration;

public class Config {
    public static class USER {
        public static final String SESSION_DIRECTORY = System.getProperty("user.dir") + "/Data/UserSession/session";
    }
    public static class FILE_EXPORT {
        public static final String XLSX_TEMPLATE_DIRECTORY = System.getProperty("user.dir") + "/Data/FileExport/XLSXTemplate/template.xlsx";
        public static final String XLSX_DEFAULT_OUTPUT_DIRECTORY = System.getProperty("user.dir") + "/Data/FileExport/Output";
    }
    public static class WEB_CLIENT {
        public static final String BASE_URL = "http://localhost:8080";
        public static final int TIME_OUT = 10;
    }
}
