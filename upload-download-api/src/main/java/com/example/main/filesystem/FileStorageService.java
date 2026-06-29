package com.example.main.filesystem;

import com.example.main.exception.FileStorageException;
import com.example.main.exception.StorageFileNotFoundException;
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

    private final Path fileStorageLocation;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getDrive()).toAbsolutePath().normalize();
        try {
            if (Files.notExists(this.fileStorageLocation)) {
                Files.createDirectory(this.fileStorageLocation);
            }
        } catch (IOException exception) {
            throw new FileStorageException("Could not create the directory uploaded.", exception);
        }
    }

    // walk将会获取指定目录下的所有文件
    public List<String> getAllDownloadFiles() throws IOException {
        Path downloadFolder = this.fileStorageLocation.resolve("download");
        return Files.walk(downloadFolder)
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .sorted()
                .toList();
    }

    // 上传相同文件自动覆盖同名文件(或追加随机ID)
    public String storeFile(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    // 获取下载文件的资源
    public Resource loadFileResourceByName(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
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
