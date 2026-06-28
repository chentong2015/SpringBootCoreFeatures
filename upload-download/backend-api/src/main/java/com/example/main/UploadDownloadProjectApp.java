package com.example.main;

import com.example.main.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class UploadDownloadProjectApp {

    public static void main(String[] args) {
        SpringApplication.run(UploadDownloadProjectApp.class, args);
    }
}
