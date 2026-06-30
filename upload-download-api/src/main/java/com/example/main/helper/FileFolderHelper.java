package com.example.main.helper;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileFolderHelper {

    private FileFolderHelper() {}

    public static void createFolder(Path folder) {
        try {
            if (Files.notExists(folder)) {
                Files.createDirectory(folder);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Chunk folder can't be created", exception);
        }
    }

    public static List<String> getFilenamesUnderFolder(Path folder) throws IOException {
        return Files.walk(folder)
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .sorted()
                .toList();
    }
}
