package com.example.main.controller;

import com.example.main.filesystem.FileStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class AllFilesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileStorageService fileStorageService;

    @Test
    public void shouldListAllFiles() throws Exception {
        List<String> filenames = List.of("first.txt", "second.txt");
        BDDMockito.given(this.fileStorageService.getAllFilenames()).willReturn(filenames);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/file/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("[\"first.txt\", \"second.txt\"]"));
    }
}
