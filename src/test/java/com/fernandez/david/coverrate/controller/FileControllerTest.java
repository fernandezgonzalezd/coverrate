package com.fernandez.david.coverrate.controller;


import com.fernandez.david.coverrate.exception.CoverrateException;
import com.fernandez.david.coverrate.model.ResponseFile;
import com.fernandez.david.coverrate.service.FileCompress;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerTest {

    private ResponseFile responseFile;
    private List<MultipartFile> mockFiles;

    @Mock
    private FileCompress fileCompress;

    private FileController fileController;

    @Before
    public void setUp() throws IOException {
        openMocks(this);
        responseFile = buildResponseFile(File.createTempFile("test", null));
        mockFiles = buildMockFiles();

        fileController = new FileController(fileCompress);
    }

    @Test
    public void uploadMultipleFilesTest() {
        when(fileCompress.compressFiles(mockFiles, "coverrateTest.zip")).thenReturn(responseFile);

        ResponseEntity<Resource> resourceResponseEntity = fileController.uploadMultipleFiles(mockFiles);

        assertEquals(HttpStatus.OK, resourceResponseEntity.getStatusCode());
        assertNotNull(responseFile.getFile());
    }

    @Test(expected = CoverrateException.class)
    public void fileCompressThrowsExceptionTest() {
        when(fileCompress.compressFiles(mockFiles, "coverrateTest.zip")).thenThrow(new CoverrateException("error"));

        fileController.uploadMultipleFiles(mockFiles);
    }

    @Test(expected = CoverrateException.class)
    public void responseFileThrowsExceptionTest() {
        when(fileCompress.compressFiles(mockFiles, "coverrateTest.zip")).thenReturn(buildResponseFile(new File("notExisting.txt")));

        fileController.uploadMultipleFiles(mockFiles);
    }

    private ResponseFile buildResponseFile(File file) {
        return new ResponseFile(file);
    }

    private List<MultipartFile> buildMockFiles() {
        MultipartFile mockMultipartFile = new MockMultipartFile("user-file","test.txt",
                "text/plain", "test data".getBytes());

        MultipartFile otherMockMultipartFile = new MockMultipartFile("user-file","test2.pdf",
                "text/pdf", "test data".getBytes());

        return Arrays.asList(mockMultipartFile, otherMockMultipartFile);
    }
}