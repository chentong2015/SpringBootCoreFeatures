package com.spring.tester1.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.tester1.exception.InternalServerException;
import com.spring.tester1.model.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHelper {

    private RestTemplateHelper() {
    }

    public static String sendGetRequest(RestTemplate restTemplate, String url) {
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
        return response;
    }

    // TODO. 使用自定义注入的Bean对象发送POST请求
    // 抛出的InternalServerException异常将被ControllerAdvice处理
    public static String sendPostRequest(RestTemplate restTemplate, String url, Product product)  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(parseJsonBody(product), headers);
        return restTemplate.postForObject(url, request, String.class);
    }

    private static String parseJsonBody(Product product) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException exception) {
            throw new InternalServerException("Json Processing Failure");
        }
    }
}
