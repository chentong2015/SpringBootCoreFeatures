package com.example.main.controller;

import com.example.main.filesystem.FilesystemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileSystemControllerIT {

    @Autowired
    private RestTemplate restTemplate;

    @MockitoBean
    private FilesystemService storageService;

    @LocalServerPort
    private int port;

    @Test
    void shouldUploadFile() {
        ClassPathResource resource = new ClassPathResource("sample.txt", getClass());
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", resource);

        ResponseEntity<String> response = this.restTemplate.postForEntity("/", map, String.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertTrue(response.getHeaders().getLocation().toString().startsWith("http://localhost:" + this.port + "/"));
        BDDMockito.then(storageService).should().store(ArgumentMatchers.any(MultipartFile.class));
    }

    @Test
    void shouldDownloadFile() {
        ClassPathResource resource = new ClassPathResource("sample.txt", getClass());
        BDDMockito.given(this.storageService.loadAsResource("sample.txt")).willReturn(resource);

        ResponseEntity<String> response = this.restTemplate.getForEntity("/files/{filename}", String.class, "sample.txt");

        Assertions.assertSame(response.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals("attachment; filename=\"sample.txt\"", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        Assertions.assertEquals("Spring Framework", response.getBody());
    }
}
