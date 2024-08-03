package com.huy.appnoithat.DataModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultipartForm {
    private Map<String, String> params;
    private Map<String, Multipart> multipartMap;
    @Data
    @Builder
    public static class Multipart {
        private String fileName;
        private InputStream inputStream;
    }
}
