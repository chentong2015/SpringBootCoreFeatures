package com.example.main.filesystem;

import com.example.main.exception.FileStorageException;
import com.example.main.exception.StorageFileNotFoundException;
import com.example.main.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements FilesystemService {

    private final Path fileSystemLocation;

    @Autowired
    public FileSystemStorageService(FileStorageProperties properties) {
        this.fileSystemLocation = Paths.get(properties.getDrive()).toAbsolutePath().normalize();
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(fileSystemLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileStorageException("Failed to store empty file.");
            }

            Path basicPath = Paths.get(Objects.requireNonNull(file.getOriginalFilename()));
            Path destinationFile = fileSystemLocation.resolve(basicPath).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(fileSystemLocation.toAbsolutePath())) {
                throw new FileStorageException("Security check: Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(fileSystemLocation, 1)
                    .filter(path -> !path.equals(fileSystemLocation))
                    .map(fileSystemLocation::relativize);
        } catch (IOException e) {
            throw new FileStorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Path load(String filename) {
        return fileSystemLocation.resolve(filename);
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(fileSystemLocation.toFile());
    }
}
