package com.example.main.controller;

import com.example.main.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import javax.print.DocFlavor;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/v1/file")
public class FileDownloadController {

    private final FileStorageService fileStorageService;

    public FileDownloadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/download/all")
    public ResponseEntity<List<String>> getAllDownloadFiles() {
        try {
            List<String> fileList = this.fileStorageService.getAllDownloadFiles();
            return ResponseEntity.ok().body(fileList);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to get all filenames");
        }
    }

    // TODO. 直接发送get请求下载文件
    @GetMapping("/download/json")
    public ResponseEntity<String> downloadJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<Integer, String> object = new HashMap<>();
        object.put(1, "value12");
        object.put(2, "value14");
        object.put(3, "value17");
        String fileName = "test.json";
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileName))
                .body(objectMapper.writeValueAsString(object));
    }

    // TODO. 直接在流量器输入指定的路径下载文件
    // http://localhost:8080/v1/file/download/test.txt
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileResourceByName(fileName);
        String contentType = parseContentType(resource, request);

        // 确保后端设置响应头, 以便浏览器弹出下载(而非打开文件)
        String headerValues = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(resource);
    }

    // 解析媒体文件类型 text/plain, application/xml, application/json
    private String parseContentType(Resource resource, HttpServletRequest request) {
        try {
            String absPath = resource.getFile().getAbsolutePath();
            return request.getServletContext().getMimeType(absPath);
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }
        return "application/octet-stream";
    }
}
