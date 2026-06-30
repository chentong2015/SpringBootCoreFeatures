package com.example.main.controller;

import com.example.main.service.FileStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 直接使用依赖注入的Service对象
    @Autowired
    private FileStorageService fileStorageService;

    // 发送File文件到特定的Endpoint
    @Test
    public void shouldUploadSingleFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "temp.txt", "text/plain", "Spring Framework".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/file/upload/single").file(multipartFile))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("Finish upload filename: temp.txt"));
        Assertions.assertNotNull(this.fileStorageService.loadFileResourceByName("temp.txt"));
    }
}
