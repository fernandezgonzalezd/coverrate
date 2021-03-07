package com.fernandez.david.coverrate.controller;

import com.fernandez.david.coverrate.model.ResponseFile;
import com.fernandez.david.coverrate.service.FileCompress;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class FileController {

    private static final String FILE_NAME = "coverrateTest.zip";
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileCompress fileCompress;

    @Autowired
    public FileController(FileCompress fileCompress) {
        this.fileCompress = fileCompress;
    }

    @PostMapping("/uploadMultipleFiles")
    @ResponseBody
    public ResponseEntity<ResponseFile> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
        ResponseFile responseFile = fileCompress.compressFiles(files, FILE_NAME);
        logger.info("uploadMultipleFiles (" + files.size() +") " + responseFile.getFile().getName());
        return ResponseEntity.ok().body(responseFile);
    }
}
