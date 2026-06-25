package com.example.main.controller;

import com.example.main.storage.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController("/v1/file")
public class AllFilesController {

    private final FileStorageService fileStorageService;

    public AllFilesController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllFiles() {
        try {
            List<String> responseBody = this.fileStorageService.getAllFilenames();
            return ResponseEntity.ok().body(responseBody);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to get all filenames");
        }
    }
}
