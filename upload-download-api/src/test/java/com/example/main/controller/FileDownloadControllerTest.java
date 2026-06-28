package com.example.main.controller;

import com.example.main.filesystem.FileStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FileDownloadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileStorageService fileStorageService;

    // 从test/resources目录下载特定文件资源
    @Test
    public void shouldDownloadFile() throws Exception {
        String filename = "sample.txt";
        Resource resource = new DefaultResourceLoader().getResource(filename);
        BDDMockito.given(this.fileStorageService.loadFileResourceByName(filename)).willReturn(resource);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/file/download/" + filename))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\""))
                .andExpect(content().string("this is a sample test"));
    }
}

