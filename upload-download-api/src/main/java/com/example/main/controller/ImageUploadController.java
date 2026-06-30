package com.example.main.controller;

import com.example.main.service.ImageStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/image")
public class ImageUploadController {

    private final ImageStorageService imageStorageService;

    public ImageUploadController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping("/upload/single")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile image) {
        this.imageStorageService.storeImage(image);
        String responseBody = "Success upload image: " + image.getOriginalFilename();
        return ResponseEntity.ok().body(responseBody);
    }
}
