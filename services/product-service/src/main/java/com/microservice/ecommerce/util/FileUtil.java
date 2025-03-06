package com.microservice.ecommerce.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 9:42 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
@Log4j2
public class FileUtil {

    public static String saveFile(MultipartFile file,final String baseDirectory) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File tải lên trống, không thể lưu.");
            }

            File directory = new File(baseDirectory);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException("Không thể tạo thư mục: " + baseDirectory);
            }

            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID() + fileExtension;
            Path filePath = Paths.get(baseDirectory + newFileName);

            log.info("Saving file to: {}", filePath.toAbsolutePath());

            Files.write(filePath, file.getBytes());

            return filePath.toString();
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
