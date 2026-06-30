package com.example.main.helper;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO. 文件安全控制: 名称(路径)判断 / 文件扫描 / 数据解析
public class FileValidatorHelper {

    private FileValidatorHelper() {}

    public static boolean isValidateFile(MultipartFile file) {
        if (file.isEmpty() && containsInvalidPath(file)) {
            throw new RuntimeException("File is empty");
        }

        if (!checkNormalNameFile(file.getResource().getFilename())) {
            throw new RuntimeException("File has an invalid name");
        }
        return checkNoVirus(file);
    }

    private static boolean containsInvalidPath(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        return fileName.contains("..");
    }

    private static boolean checkNormalNameFile(String nameFile) {
        Pattern pattern = Pattern.compile("^[\\w\\s-—()]{1,50}\\.\\w{1,15}$");
        Matcher matcher = pattern.matcher(nameFile);
        return matcher.find();
    }

    // 对文件做病毒扫描检测
    private static boolean checkNoVirus(MultipartFile file) {
        // AntiVirusService.scan(file);
        return true;
    }
}
