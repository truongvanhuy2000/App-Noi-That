package com.huy.appnoithat.Service.RestService;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.DataModel.SavedFileDTO;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Service.WebClient.ApacheHttpClient;
import com.huy.appnoithat.Session.UserSessionManagerImpl;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class FileStorageRestServiceIntegrationTest {
    UserSessionManagerImpl userSessionManagerImpl = Mockito.mock();
    ObjectMapper objectMapper = new ObjectMapper();
    HttpClient httpclient = HttpClients.createDefault();
    @Spy
    ApacheHttpClient apacheHttpClient = new ApacheHttpClient(httpclient, userSessionManagerImpl, objectMapper);
    @Spy
    FileStorageRestService fileStorageRestService = new FileStorageRestService(apacheHttpClient);
    TokenRestService tokenRestService = new TokenRestService(apacheHttpClient);
    @BeforeEach
    void setUp() {
        Optional<Token> token = tokenRestService.login("test", "test");
        assertTrue(token.isPresent());
        given(userSessionManagerImpl.getToken()).willReturn(token.get());
        given(userSessionManagerImpl.isAccessTokenExpired()).willReturn(false);
    }

    @Test
    void saveNtFile() {
        long contentLength = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        Optional<SavedFileDTO> optional = fileStorageRestService.saveNtFile(inputStream, "test");
        assertTrue(optional.isPresent());
        assertEquals("test", optional.get().getFileName());
    }

    @Test
    void updateNtFile() throws InterruptedException {
        long contentLength = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        Optional<SavedFileDTO> optional = fileStorageRestService.saveNtFile(inputStream, "test");
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        long newContentLength = 5 * 1024;
        ByteBuffer newByteBuffer = ByteBuffer.allocate((int) newContentLength);
        InputStream newInputStream = new ByteArrayInputStream(newByteBuffer.array());
        fileStorageRestService.updateNtFile(newInputStream, "test", optional.get().getId());
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        Optional<ByteBuffer> optionalByteBuffer = fileStorageRestService.getNtFile(optional.get().getId());
        assertTrue(optionalByteBuffer.isPresent());
        assertEquals(optionalByteBuffer.get().array().length, newContentLength);
    }

    @Test
    void getNtFileList() {
        long contentLength = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        Optional<SavedFileDTO> savedFileDTOOptional = fileStorageRestService.saveNtFile(inputStream, "test");
        Optional<List<SavedFileDTO>> optionalSavedFileDTOS = fileStorageRestService.getNtFileList();
        assertTrue(optionalSavedFileDTOS.isPresent());
        assertFalse(optionalSavedFileDTOS.get().isEmpty());
        assertTrue(optionalSavedFileDTOS.get().stream().anyMatch(item -> item.getId() == savedFileDTOOptional.get().getId()));
    }

    @Test
    void getNtFile() throws InterruptedException {
        long contentLength = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        Optional<SavedFileDTO> savedFileDTOOptional = fileStorageRestService.saveNtFile(inputStream, "test");
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        Optional<ByteBuffer> optionalByteBuffer = fileStorageRestService.getNtFile(savedFileDTOOptional.get().getId());
        assertTrue(optionalByteBuffer.isPresent());
        assertEquals(optionalByteBuffer.get().array().length, contentLength);
    }

    @Test
    void deleteNtFile() throws InterruptedException {
        long contentLength = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        Optional<SavedFileDTO> savedFileDTOOptional = fileStorageRestService.saveNtFile(inputStream, "test");
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        fileStorageRestService.deleteNtFile(savedFileDTOOptional.get().getId());
        Optional<ByteBuffer> optionalByteBuffer = fileStorageRestService.getNtFile(savedFileDTOOptional.get().getId());
        assertTrue(optionalByteBuffer.isEmpty());
        Optional<SavedFileDTO> savedFileDTO = fileStorageRestService.getFileInfo(savedFileDTOOptional.get().getId());
        assertTrue(savedFileDTO.isEmpty());
    }

    @Test
    void updateFileInfo() throws InterruptedException {
        long contentLength = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        Optional<SavedFileDTO> savedFileDTOOptional = fileStorageRestService.saveNtFile(inputStream, "test");
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        String newName = "test123";
        fileStorageRestService.updateFileInfo(savedFileDTOOptional.get().getId(), SavedFileDTO.builder().fileName(newName).build());
        Optional<SavedFileDTO> savedFileDTO = fileStorageRestService.getFileInfo(savedFileDTOOptional.get().getId());
        assertTrue(savedFileDTO.isPresent());
        assertEquals(savedFileDTO.get().getFileName(), newName);
    }

    @Test
    void getFileInfo() throws InterruptedException {
        long contentLength = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        String fileName = UUID.randomUUID().toString();
        Optional<SavedFileDTO> savedFileDTOOptional = fileStorageRestService.saveNtFile(inputStream, fileName);
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        Optional<SavedFileDTO> savedFileDTO = fileStorageRestService.getFileInfo(savedFileDTOOptional.get().getId());
        assertTrue(savedFileDTO.isPresent());
        assertEquals(savedFileDTO.get().getFileName(), fileName);
    }
}