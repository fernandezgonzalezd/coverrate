package com.fernandez.david.coverrate.service;

import com.fernandez.david.coverrate.exception.CoverrateException;
import com.fernandez.david.coverrate.model.ResponseFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipCompress implements FileCompress {

    @Override
    public ResponseFile compressFiles(List<MultipartFile> files, String fileName)  {

        List<File> convertedFiles;
        ResponseFile responseFile;

        try {
            convertedFiles = convertMultipartFiles(files);

            File file = zipFiles(fileName, convertedFiles);
            responseFile = new ResponseFile(file);
        } catch (IOException e) {
            throw new CoverrateException("Could not compressFiles. Please try again!");
        }

        return responseFile;
    }

    private List<File> convertMultipartFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<File> convertedFiles = new ArrayList<>();

        for(MultipartFile multipartFile: multipartFiles) {


            File convertedFile = new File(multipartFile.getOriginalFilename());
            convertedFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convertedFile);
            fos.write(multipartFile.getBytes());
            fos.close();

            convertedFiles.add(convertedFile);
        }
        return convertedFiles;
    }

    private static File zipFiles(String zipFileName, List<File> addToZip) throws IOException {

        String zipPath = System.getProperty("java.io.tmpdir") + File.separator + zipFileName;
        new File(zipPath).delete(); //delete if zip file already exist

        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(
                     new BufferedOutputStream(fos))) {
            zos.setLevel(9); //level of compression

            for (File file : addToZip) {
                if (file.exists()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        ZipEntry entry = new ZipEntry(file.getName());
                        zos.putNextEntry(entry);
                        for (int c = fis.read(); c != -1; c = fis.read()) {
                            zos.write(c);
                        }
                        zos.flush();
                    }
                }
            }
        }
        File zip = new File(zipPath);
        if (!zip.exists()) {
            throw new FileNotFoundException("The created zip file could not be found");
        }
        return zip;
    }
}
