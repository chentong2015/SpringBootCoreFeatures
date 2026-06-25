package com.example.main.filesystem;

import com.example.main.exception.FileStorageException;
import com.example.main.property.FileStorageProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemStorageServiceTest {

    private FileStorageProperties properties = new FileStorageProperties();
    private FileSystemStorageService service;

    @BeforeEach
    public void init() {
        properties.setDrive("target/files/" + Math.abs(new Random().nextLong()));
        service = new FileSystemStorageService(properties);
        service.deleteAll();
        service.init();
    }

    @Test
    public void loadNonExistent() {
        assertThat(service.load("foo.txt")).doesNotExist();
    }

    @Test
    public void saveAndLoad() {
        service.store(getMockFile("foo.txt"));
        assertThat(service.load("foo.txt")).exists();
    }

    @Test
    public void saveRelativePathNotPermitted() {
        Assertions.assertThrows(FileStorageException.class, () ->
                service.store(getMockFile("../foo.txt"))
        );
    }

    @Test
    public void saveAbsolutePathNotPermitted() {
        Assertions.assertThrows(FileStorageException.class, () -> {
            service.store(getMockFile("/etc/passwd"));
        });
    }

    @Test
    @EnabledOnOs({OS.LINUX})
    public void saveAbsolutePathInFilenamePermitted() {
        // Unix file systems (e.g. ext4) allows backslash '\' in file names.
        String fileName = "\\etc\\passwd";
        service.store(getMockFile(fileName));
        Assertions.assertTrue(Files.exists(Paths.get(properties.getDrive()).resolve(Paths.get(fileName))));
    }

    @Test
    public void savePermitted() {
        service.store(getMockFile("bar/../foo.txt"));
    }

    private MultipartFile getMockFile(String originalFilename) {
        byte[] content = "Hello, World".getBytes();
        return new MockMultipartFile("foo", originalFilename, MediaType.TEXT_PLAIN_VALUE, content);
    }
}
