package net.axel.sharehope.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.exception.domains.FileUploadException;
import net.axel.sharehope.service.FileUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryFileUploader implements FileUploader {

    private final Cloudinary cloudinary;

    @Value("${app.cloudinary.upload.base-folder}")
    private String baseFolder;

    @Override
    public String upload(MultipartFile file, String folderPath) {
        validateFile(file);
        try {
            Map<String, Object> uploadOptions = ObjectUtils.asMap(
                    "folder", baseFolder + "/" + folderPath,
                    "resource_type", "auto"
            );
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload file to Cloudinary: " + e.getMessage(), e);
        }
    }


    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new FileUploadException("File size exceeds 10MB");
        }
    }
}
