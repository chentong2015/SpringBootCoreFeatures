package org.springboot.async;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootAsyncTaskApplication {

    @Autowired
    private AsyncTaskExecutionEngine executionEngine;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAsyncTaskApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        try {
            executionEngine.startImportTasks();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
