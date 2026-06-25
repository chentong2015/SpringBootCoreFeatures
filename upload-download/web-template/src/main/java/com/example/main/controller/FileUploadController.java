package com.example.main.controller;

import com.example.main.storage.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("/v1/file")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload/single")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = this.fileStorageService.storeFile(file);
        String responseBody = "Finish upload filename: " + fileName;
        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<String> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        String responseBody = "Finish upload filenames: ";
        for (MultipartFile file: files) {
            String filename = this.fileStorageService.storeFile(file);
            responseBody.concat(filename + "; ");
        }
        return ResponseEntity.ok().body(responseBody);
    }
}
