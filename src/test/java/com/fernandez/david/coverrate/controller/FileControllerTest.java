package com.fernandez.david.coverrate.controller;


import com.fernandez.david.coverrate.model.ResponseFile;
import com.fernandez.david.coverrate.service.FileCompress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerTest {

    private ResponseFile responseFile;
    private List<MultipartFile> mockFiles;

    @Mock
    private FileCompress fileCompress;

    private FileController fileController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        responseFile = buildResponseFile();
        mockFiles = buildMockFiles();

        fileController = new FileController(fileCompress);
    }

    @Test
    public void uploadMultipleFilesTest() {
        when(fileCompress.compressFiles(mockFiles, "coverrateTest.zip")).thenReturn(responseFile);

        ResponseEntity<ResponseFile> resourceResponseEntity = fileController.uploadMultipleFiles(mockFiles);

        assertEquals(HttpStatus.OK, resourceResponseEntity.getStatusCode());
        assertEquals(responseFile, resourceResponseEntity.getBody());
    }

    private ResponseFile buildResponseFile() {
        return new ResponseFile(new File("empty.txt"));
    }

    private List<MultipartFile> buildMockFiles() {
        MultipartFile mockMultipartFile = new MockMultipartFile("user-file","test.txt",
                "text/plain", "test data".getBytes());

        MultipartFile otherMockMultipartFile = new MockMultipartFile("user-file","test2.pdf",
                "text/pdf", "test data".getBytes());

        return Arrays.asList(mockMultipartFile, otherMockMultipartFile);
    }
}