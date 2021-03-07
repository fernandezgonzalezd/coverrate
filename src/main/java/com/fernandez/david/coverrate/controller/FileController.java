package com.fernandez.david.coverrate.controller;

import com.fernandez.david.coverrate.exception.CoverrateException;
import com.fernandez.david.coverrate.model.ResponseFile;
import com.fernandez.david.coverrate.service.FileCompress;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private static final String FILE_NAME = "coverrateTest.zip";

    private final FileCompress fileCompress;

    @Autowired
    public FileController(FileCompress fileCompress) {
        this.fileCompress = fileCompress;
    }

    @PostMapping(value = "/uploadMultipleFiles", produces = "application/zip")
    public ResponseEntity<Resource> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
        logger.info("uploadMultipleFiles (" + files.size() + ")");
        ResponseFile responseFile = fileCompress.compressFiles(files, FILE_NAME);

        return buildResponse(responseFile.getFile());
    }

    private ResponseEntity<Resource> buildResponse(File file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);

            Resource resource = new InputStreamResource(inputStream);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            logger.error("Error ", e);
            throw new CoverrateException("Could not compressFiles. Please try again!", e);
        }

    }

}
