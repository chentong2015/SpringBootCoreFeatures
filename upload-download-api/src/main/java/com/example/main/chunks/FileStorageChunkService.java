package com.example.main.chunks;

import com.example.main.exception.FileStorageException;
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
public class FileStorageChunkService {

    private final Path fileStorageLocation;

    public FileStorageChunkService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getDrive()).toAbsolutePath().normalize();
        createFolderIfNotExist(this.fileStorageLocation);
    }

    private void createFolderIfNotExist(Path folder) {
        try {
            if (Files.notExists(folder)) {
                Files.createDirectory(folder);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Chunk folder can't be created", exception);
        }
    }

    // 基于fileId创建特定的临时目录存储chunk, 存放所有Chunk文件(基于Index坐标)
    public String storeChunk(String fileId, int index, MultipartFile multipartFile) {
        Path chunkFolder = this.fileStorageLocation.resolve(fileId);
        createFolderIfNotExist(chunkFolder);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new FileStorageException("Filename contains invalid path sequence " + fileName);
        }
        try {
            fileName = "chunk_" + index + "_" + fileName;
            Path targetLocation = chunkFolder.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store chunk file " + fileName, ex);
        }
    }

    // 等上传结束后自动合并Chunk分片, 将合并文件提供下载
    public void mergeChunks(String fileId) {
        Path chunkFolder = this.fileStorageLocation.resolve(fileId);
        String filename = fileId.substring(0, fileId.lastIndexOf("-"));
        Path downloadFilepath = this.fileStorageLocation.resolve("download").resolve(filename);
        try {
            List<String> chunkFiles = Files.walk(chunkFolder)
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .toList();
            try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(downloadFilepath))) {
                for (String chunk : chunkFiles) {
                    mergeSingleChunk(bos, chunkFolder, chunk);
                }
            }
        } catch (IOException exception) {
            System.out.println("Parse chunk file failed");
        }
    }

    private void mergeSingleChunk(BufferedOutputStream bos, Path chunkFolder, String chunk) throws IOException {
        File file = Paths.get(chunkFolder.toString(), chunk).toFile();
        if (!file.exists()) {
            throw new FileNotFoundException(chunk + " could not be found");
        }
        try (InputStream is = new FileInputStream(file)) {
            IOUtils.copyLarge(is, bos);
        } finally {
            // Files.deleteIfExists(file.toPath());
        }
    }
}
