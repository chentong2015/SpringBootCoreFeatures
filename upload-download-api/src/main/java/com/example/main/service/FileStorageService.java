package com.example.main.service;

import com.example.main.exception.FileStorageException;
import com.example.main.exception.StorageFileNotFoundException;
import com.example.main.helper.FileFolderHelper;
import com.example.main.helper.FileValidatorHelper;
import com.example.main.property.FileStorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageService {

    private final Path rootLocation;
    private static final String DOWNLOAD_FOLDER = "download";

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.rootLocation = Paths.get(fileStorageProperties.getDrive()).toAbsolutePath().normalize();
        FileFolderHelper.createFolder(this.rootLocation);
    }

    // 上传相同文件自动覆盖同名文件(或追加随机ID)
    public String storeFile(MultipartFile file) {
        if (!FileValidatorHelper.isValidateFile(file)) {
            throw new RuntimeException("Not a valid file !");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = this.rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    // walk将会获取指定目录下的所有文件
    public List<String> getAllDownloadFiles() throws IOException {
        Path downloadFolder = this.rootLocation.resolve(DOWNLOAD_FOLDER);
        return FileFolderHelper.getFilenamesUnderFolder(downloadFolder);
    }

    // 获取下载文件的资源
    public Resource loadFileResourceByName(String fileName) {
        try {
            Path filePath = this.rootLocation.resolve(DOWNLOAD_FOLDER).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new StorageFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
