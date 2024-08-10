package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huy.appnoithat.DataModel.MultipartForm;
import com.huy.appnoithat.DataModel.SavedFileDTO;
import com.huy.appnoithat.DataModel.WebClient.Response;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import lombok.RequiredArgsConstructor;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class FileStorageRestService {
    public static class Path {
        private static final URI BASE_URL = URIBuilder.empty().addPath("api", "file-storage").toURI();
        public static final URI UPLOAD_NT_FILE = URIBuilder.fromURI(BASE_URL).addPath("upload", "nt-file").toURI();
        public static final URI GET_ALL = URIBuilder.fromURI(BASE_URL).addPath("get-all").toURI();
        public static final Function<String, URI> DOWNLOAD_NT_FILE = fileId -> URIBuilder.fromURI(BASE_URL)
                .addPath("download", "nt-file", fileId).toURI();
        public static final Function<String, URI> DELETE_NT_FILE = fileId -> URIBuilder.fromURI(BASE_URL)
                .addPath("delete-nt-file", fileId).toURI();
        public static final Function<String, URI> UPDATE_NT_FILE = fileId -> URIBuilder.fromURI(BASE_URL)
                .addPath("upload", "nt-file", fileId).toURI();
        public static final Function<String, URI> GET_FILE_INFO = fileId -> URIBuilder.fromURI(BASE_URL)
                .addPath("get-file", fileId).toURI();
        public static final Function<String, URI> UPDATE_FILE_INFO = fileId -> URIBuilder.fromURI(BASE_URL)
                .addPath("update-nt-file", fileId).toURI();
    }
    private final WebClientService webClientService;

    public void updateNtFile(InputStream file, String fileName, int fileId) {
        URI uri = Path.UPDATE_NT_FILE.apply(String.valueOf(fileId));
        MultipartForm.Multipart multipart = MultipartForm.Multipart.builder()
                .fileName(fileName)
                .inputStream(file)
                .build();
        MultipartForm multipartForm = MultipartForm.builder()
                .multipartMap(Map.of("file", multipart))
                .build();
        Response<Object> response = webClientService.authorizedPutMultipartUpload(URIBuilder.fromURI(uri), multipartForm, null);
        if (!response.isSuccess()) {
            // TODO: Handle error response
        }
    }

    public Optional<SavedFileDTO> saveNtFile(InputStream file, String fileName) {
        TypeReference<SavedFileDTO> typeReference = new TypeReference<>() {};
        MultipartForm.Multipart multipart = MultipartForm.Multipart.builder()
                .fileName(fileName)
                .inputStream(file)
                .build();
        MultipartForm multipartForm = MultipartForm.builder()
                .multipartMap(Map.of("file", multipart))
                .build();
        Response<SavedFileDTO> response = webClientService.authorizedPostMultipartUpload(
                URIBuilder.fromURI(Path.UPLOAD_NT_FILE), multipartForm, typeReference);
        if (response.isSuccess()) {
            return response.getResponse();
        } else {
            // TODO: Handle error response
            return Optional.empty();
        }
    }

    public Optional<List<SavedFileDTO>> getNtFileList() {
        TypeReference<List<SavedFileDTO>> listTypeReference = new TypeReference<>() {};
        Response<List<SavedFileDTO>> response = webClientService.authorizedHttpGet(URIBuilder.fromURI(Path.GET_ALL), listTypeReference);
        if (response.isSuccess()) {
            return response.getResponse();
        } else {
            // TODO: Handle error response
            return Optional.empty();
        }
    }

    public Optional<ByteBuffer> getNtFile(Integer fileId) {
        URI uri = Path.DOWNLOAD_NT_FILE.apply(fileId.toString());
        Response<InputStream> response = webClientService.authorizedHttpGetFile(URIBuilder.fromURI(uri));
        if (response.isSuccess()) {
            return response.getRawResponse();
        } else {
            // TODO: Handle error response
            return Optional.empty();
        }
    }

    /**
     * @param fileId
     *  When using this method, must check if the file is already upload to s3
     */
    public void deleteNtFile(Integer fileId) {
        URI uri = Path.DELETE_NT_FILE.apply(fileId.toString());
        Response<Object> response = webClientService.authorizedHttpDelete(URIBuilder.fromURI(uri), null);
        if (!response.isSuccess()) {
            // TODO: Handle error response
        }
    }

    public void updateFileInfo(Integer fileId, SavedFileDTO savedFileDTO) {
        URI uri = Path.UPDATE_FILE_INFO.apply(fileId.toString());
        Response<Object> response = webClientService.authorizedHttpPut(URIBuilder.fromURI(uri), savedFileDTO,null);
        if (!response.isSuccess()) {
            // TODO: Handle error response
        }
    }

    public Optional<SavedFileDTO> getFileInfo(Integer fileId) {
        TypeReference<SavedFileDTO> typeReference = new TypeReference<>() {};
        URI uri = Path.GET_FILE_INFO.apply(fileId.toString());
        Response<SavedFileDTO> response = webClientService.authorizedHttpGet(URIBuilder.fromURI(uri),typeReference);
        if (response.isSuccess()) {
            return response.getResponse();
        } else {
            // TODO: Handle error notification here
            return Optional.empty();
        }
    }
}
