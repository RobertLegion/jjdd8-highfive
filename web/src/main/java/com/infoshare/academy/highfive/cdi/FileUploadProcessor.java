package com.infoshare.academy.highfive.cdi;

import com.infoshare.academy.highfive.exception.JsonFileNotFound;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequestScoped
public class FileUploadProcessor {

    private String TEMP = "/tmp/hfv/";

//    public File uploadJsonFile(Part filePart) throws IOException, JsonFileNotFound {
//
//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//
//        if (fileName == null || fileName.isEmpty()) {
//            throw new JsonFileNotFound("No JSON file found found!");
//        }
//        File uploadDir = new File(TEMP);
//        if (!uploadDir.exists()) uploadDir.mkdir();
//
//        File file = new File( TEMP + fileName);
//
//        Files.deleteIfExists(file.toPath());
//
//        InputStream fileContent = filePart.getInputStream();
//
//        Files.copy(fileContent, file.toPath());
//
//        fileContent.close();
//
//        return file;
//    }
}
