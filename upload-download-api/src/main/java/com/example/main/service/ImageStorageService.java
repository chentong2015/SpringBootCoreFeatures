package com.example.main.service;

import com.example.main.helper.FileFolderHelper;
import com.example.main.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageStorageService {

    @Value("${spring.servlet.multipart.max-file-size}")
    private Long maxSize;

    private final String PNG = "image/png";
    private final String JPEG = "image/jpeg";

    private final Path fileStorageLocation;

    public ImageStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getDrive()).toAbsolutePath().normalize();
        FileFolderHelper.createFolder(this.fileStorageLocation);
    }

    // 用户上传Avatar图片时必须检查并验证
    public void storeImage(MultipartFile image) {
        String mimeType = image.getContentType();
        if (!checkImageType(mimeType) || image.getSize() > maxSize) {
            throw new RuntimeException("Mime type: " + mimeType + ". Size" + image.getSize());
        }
        if (!isValidFileSize(new File(Objects.requireNonNull(image.getOriginalFilename())))) {
            throw new RuntimeException();
        }

        String uuidFile = UUID.randomUUID().toString();
        String fileExtension = com.google.common.io.Files.getFileExtension(image.getOriginalFilename());
        String resultFileName = fileStorageLocation.toString() + uuidFile + "." + fileExtension;
        try {
            image.transferTo(new File(resultFileName));
            // Files.write(Paths.get(resultFileName), image.getBytes());
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store image file");
        }
    }

    private boolean checkImageType(String type) {
        return type != null && (type.equals(JPEG) || type.equals(PNG));
    }

    private boolean isValidFileSize(File file) {
        return (file.length() / 1024 / 1024) < 5;
    }

    // TODO. 下载特定URL链接的文件, 发送请求前必须验证有效性
    public void downloadRemoteImage(String url) throws Exception {
        if (!(url.toLowerCase().endsWith(".png") || url.toLowerCase().endsWith(".jpg"))) {
            throw new RuntimeException();
        }
        if (!url.startsWith("https://www.xxx.com/")) {
            throw new RuntimeException();
        }
        URL urlReference = getUrlReference(url);

        StringBuilder newFileNameBuilder = new StringBuilder();
        String randomName = UUID.randomUUID().toString();
        newFileNameBuilder.append(randomName);
        Path path = Paths.get(fileStorageLocation.toString(), newFileNameBuilder.toString());
        try (InputStream in = urlReference.openStream()) {
            Files.copy(in, path);
        } catch (Exception e) {
            throw new RuntimeException("Could not download remote image");
        }
    }

    private static URL getUrlReference(String url) throws MalformedURLException {
        URL urlReference = new URL(url);
        try {
            HttpURLConnection connection = (HttpURLConnection) urlReference.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 404) {
                throw new RuntimeException();
            }
            if (connection.getResponseCode() > 299 || connection.getResponseCode() < 200) {
                throw new RuntimeException(); // any status other than 2xx
            }
        } catch (IOException e) {
            throw new RuntimeException("The supplied url was invalid");
        }
        return urlReference;
    }
}
