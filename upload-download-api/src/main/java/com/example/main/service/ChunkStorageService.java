package com.example.main.service;

import com.example.main.exception.FileStorageException;
import com.example.main.helper.FileFolderHelper;
import com.example.main.helper.FileValidatorHelper;
import com.example.main.property.FileStorageProperties;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ChunkStorageService {

    private final Path rootLocation;
    private static final String DOWNLOAD_FOLDER = "download";

    public ChunkStorageService(FileStorageProperties fileStorageProperties) {
        this.rootLocation = Paths.get(fileStorageProperties.getDrive()).toAbsolutePath().normalize();
        FileFolderHelper.createFolder(this.rootLocation);
    }

    // 基于fileId创建特定的临时目录存储chunk, 存放所有Chunk文件(基于Index坐标)
    public String storeChunk(String fileId, int index, MultipartFile file) {
        if (!FileValidatorHelper.isValidateFile(file)) {
            throw new RuntimeException("Not a valid file !");
        }

        Path chunkFolder = this.rootLocation.resolve(fileId);
        FileFolderHelper.createFolder(chunkFolder);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            fileName = "chunk_" + index + "_" + fileName;
            Path targetLocation = chunkFolder.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store chunk file " + fileName, ex);
        }
    }

    // 等上传结束后自动合并Chunk分片, 将合并文件提供下载
    public void mergeChunks(String fileId) {
        Path chunkFilesFolder = this.rootLocation.resolve(fileId);
        String filename = fileId.substring(0, fileId.lastIndexOf("-"));
        Path downloadFilename = this.rootLocation.resolve(DOWNLOAD_FOLDER).resolve(filename);
        try {
            List<String> chunkFiles = FileFolderHelper.getFilenamesUnderFolder(chunkFilesFolder);
            try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(downloadFilename))) {
                for (String chunkFilename : chunkFiles) {
                    mergeSingleChunk(bos, chunkFilesFolder, chunkFilename);
                }
            }
        } catch (IOException exception) {
            System.out.println("Parse chunk file failed");
        }
    }

    private void mergeSingleChunk(BufferedOutputStream bos, Path chunkFolder, String chunkFilename) throws IOException {
        File file = Paths.get(chunkFolder.toString(), chunkFilename).toFile();
        if (!file.exists()) {
            throw new FileNotFoundException(chunkFilename + " could not be found");
        }
        try (InputStream is = new FileInputStream(file)) {
            IOUtils.copyLarge(is, bos);
        } finally {
            // Files.deleteIfExists(file.toPath());
        }
    }
}
