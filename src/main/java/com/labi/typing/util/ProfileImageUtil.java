package com.labi.typing.util;

import com.labi.typing.exception.custom.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

public class ProfileImageUtil {

    public static final String DEFAULT_PROFILE_IMG = "default-user-profile-img.png";
    private static final String UPLOAD_PROFILE_IMG_PATH = "src/main/resources/profile-img/";
    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png");

    public static String upload(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new ValidationException("File is empty", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String extension = image.getOriginalFilename().split("\\.")[1];
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new ValidationException("File extension not allowed", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String fileName = UUID.randomUUID() + "." + image.getOriginalFilename().split("\\.")[1];

        File uploadDir = new File(UPLOAD_PROFILE_IMG_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        Path filePath = Path.of(UPLOAD_PROFILE_IMG_PATH, fileName);

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    public static byte[] recover(String profileImgUrl) throws IOException {
        Path filePath = Path.of(UPLOAD_PROFILE_IMG_PATH, profileImgUrl);
        return Files.readAllBytes(filePath);
    }

    public static void delete(String profileImgUrl) {
        if (profileImgUrl.equals(DEFAULT_PROFILE_IMG)) {
            return;
        }

        File file = new File(UPLOAD_PROFILE_IMG_PATH + profileImgUrl);

        if (file.exists()) {
            file.delete();
        }
    }
}
