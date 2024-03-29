package com.josy.project.util;

import com.josy.project.model.FileProcessingException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@UtilityClass
public class UploadUtils {
    private final Path root = Paths.get("uploads");

    /**
     * Create the upload directory at the root of the project
     * if it does not exist
     */
    public void createUploadDirectory() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            log.error("Could not create upload directory!", e);
            throw new FileProcessingException("Could not initialize folder for upload!", e);
        }
    }


    public String extractFileExtension(String fileName) {
        // exclude the dot
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public String removeSpecialCharacters(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.-]", "");
    }

}
