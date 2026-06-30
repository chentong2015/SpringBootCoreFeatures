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

    @Value("${multipart.image.max-size}")
    private Long maxSize;
    private final Path rootLocation;

    public ImageStorageService(FileStorageProperties fileStorageProperties) {
        this.rootLocation = Paths.get(fileStorageProperties.getDrive()).toAbsolutePath().normalize();
        FileFolderHelper.createFolder(this.rootLocation);
    }

    // 用户上传Avatar图片时必须检查并验证
    public void storeImage(MultipartFile image) {
        if (!checkImageType(image) || !checkImageSize(image)) {
            throw new RuntimeException("Image format wrong");
        }

        String uuid = UUID.randomUUID().toString();
        String extension = com.google.common.io.Files.getFileExtension(image.getOriginalFilename());
        String imageFilename = uuid + "." + extension;
        String imagePath = String.valueOf(rootLocation.resolve(imageFilename));
        try {
            image.transferTo(new File(imagePath));
            // Files.write(Paths.get(resultFileName), image.getBytes());
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store image file");
        }
    }

    // 设置支持的Image类型
    private boolean checkImageType(MultipartFile image) {
        String type = image.getContentType();
        return type != null && (type.equals("image/png") || type.equals("image/jpeg"));
    }

    private boolean checkImageSize(MultipartFile image) {
        long byteSize = image.getSize();
        return byteSize <= maxSize;
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
        Path path = Paths.get(rootLocation.toString(), newFileNameBuilder.toString());
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
