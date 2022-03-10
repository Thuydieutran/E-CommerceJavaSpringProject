package com.thuydieutran.admin;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/* This utility class is used for creating the directory if not exists,
        and save the uploaded file from MultipartFile object to a file in the file system.  */
@Service
public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        // If the Path doesn't exist, we create the directory of upload files
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // This is the try with resources statement in Java (inputStream)
        try (InputStream inputStream = multipartFile.getInputStream()) {
            // Create the path of the file that relates to the upload directory
            Path filePath = uploadPath.resolve(fileName);
            // StandardCopyOption.REPLACE_EXISTING (optional) will override the existing files with the same name
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException("Could not save file: " + fileName, ex);
        }
    }
}
