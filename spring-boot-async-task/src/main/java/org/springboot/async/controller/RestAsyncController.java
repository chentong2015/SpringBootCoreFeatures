package org.springboot.async.controller;

import org.springboot.async.service.AsyncTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestAsyncController {

    private final AsyncTaskService asyncTaskService;

    public RestAsyncController(AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
    }

    // TODO. 异步执行请求，并立即返回Response到客户端
    @GetMapping(value = "ping/async")
    public ResponseEntity<Map<String, String>> async() throws InterruptedException {
        this.asyncTaskService.doImport("Ping Async Endpoints");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Request is under process");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
