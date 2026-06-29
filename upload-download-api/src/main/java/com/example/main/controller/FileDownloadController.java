package com.example.main.controller;

import com.example.main.filesystem.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

    // TODO. 直接在流量器输入指定的路径, 根据文件名称下载
    // http://localhost:8080/v1/file/download/test.txt
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileResourceByName(fileName);

        // 确定Media媒体文件类型 text/plain, application/xml, application/json
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // 确保后端设置响应头, 以便浏览器弹出下载(而非打开文件)
        String headerValues = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(resource);
    }
}
