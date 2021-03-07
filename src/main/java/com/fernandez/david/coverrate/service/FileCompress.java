package com.fernandez.david.coverrate.service;

import com.fernandez.david.coverrate.model.ResponseFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileCompress {

    ResponseFile compressFiles(List<MultipartFile> files, String fileName);

}
