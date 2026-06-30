package com.example.main.controller;

import com.example.main.service.ChunkFileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/file")
public class ChunkFileUploadController {

    private final ChunkFileStorageService chunkFileStorageService;

    public ChunkFileUploadController(ChunkFileStorageService chunkFileStorageService) {
        this.chunkFileStorageService = chunkFileStorageService;
    }

    @PostMapping("/upload/chunk")
    public ResponseEntity<Void> uploadChunk(@RequestParam("file") MultipartFile file,
                                            @RequestParam("index") int index,
                                            @RequestParam("total") int total,
                                            @RequestParam("fileId") String fileId) {
        this.chunkFileStorageService.storeChunk(fileId, index, file);
        if (index == total - 1) {
            System.out.println("Start Merge all chunk files");
            this.chunkFileStorageService.mergeChunks(fileId);
        }
        return ResponseEntity.ok().build();
    }
}
