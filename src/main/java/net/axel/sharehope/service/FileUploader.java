package net.axel.sharehope.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    String upload(MultipartFile multipartFile, String folderPath);

    void delete(String publicId, String folderType);
}
