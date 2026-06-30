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

    // 文件的安全控制: 扫描 / 判断名称 / 判断路径 / 解析(数据)
    public static void isValidateFile(MultipartFile file) {
        if (checkFileForEmptiness(file)) {
            throw new RuntimeException("File is empty");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new RuntimeException("Filename contains invalid path sequence " + fileName);
        }

        if (!checkNormalNameFile(file.getResource().getFilename())) {
            throw new RuntimeException("File has an invalid name");
        }
    }

    private static boolean checkFileForEmptiness(MultipartFile file) {
        return file.isEmpty();
    }

    private static boolean checkNormalNameFile(String nameFile) {
        Pattern pattern = Pattern.compile("^[\\w\\s-—()]{1,50}\\.\\w{1,15}$");
        Matcher matcher = pattern.matcher(nameFile);
        return matcher.find();
    }
}
