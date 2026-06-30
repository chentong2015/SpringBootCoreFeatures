package com.example.main.controller;

import com.example.main.service.ChunkStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/file")
public class ChunkUploadController {

    private final ChunkStorageService chunkStorageService;

    public ChunkUploadController(ChunkStorageService chunkStorageService) {
        this.chunkStorageService = chunkStorageService;
    }

    @PostMapping("/upload/chunk")
    public ResponseEntity<Void> uploadChunk(@RequestParam("file") MultipartFile file,
                                            @RequestParam("index") int index,
                                            @RequestParam("total") int total,
                                            @RequestParam("fileId") String fileId) {
        this.chunkStorageService.storeChunk(fileId, index, file);
        if (index == total - 1) {
            System.out.println("Start Merge all chunk files");
            this.chunkStorageService.mergeChunks(fileId);
        }
        return ResponseEntity.ok().build();
    }
}
