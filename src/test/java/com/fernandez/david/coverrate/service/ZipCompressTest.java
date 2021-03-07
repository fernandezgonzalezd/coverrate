package com.fernandez.david.coverrate.service;

import com.fernandez.david.coverrate.model.ResponseFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class ZipCompressTest {

    private static final String FILE_NAME = "fileName";

    private List<MultipartFile> mockFiles;

    private FileCompress fileCompress;

    @Before
    public void setUp() {

        mockFiles = buildMockFiles();
        fileCompress = new ZipCompress();
    }

    @Test
    public void compressFilesTest() throws IOException {


        ResponseFile responseFile = fileCompress.compressFiles(mockFiles, FILE_NAME);

        assertNotNull(responseFile.getFile());
    }

    private List<MultipartFile> buildMockFiles() {
        MultipartFile mockMultipartFile = new MockMultipartFile("user-file","test.txt",
                "text/plain", "test data".getBytes());

        MultipartFile otherMockMultipartFile = new MockMultipartFile("user-file","test2.pdf",
                "text/pdf", "test data".getBytes());

        return Arrays.asList(mockMultipartFile, otherMockMultipartFile);
    }

}