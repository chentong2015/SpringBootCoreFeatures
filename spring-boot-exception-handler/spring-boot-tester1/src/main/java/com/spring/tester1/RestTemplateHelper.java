package com.spring.tester1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.tester1.advice.exception.InternalServerException;
import com.spring.tester1.controller.model.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHelper {

    private RestTemplateHelper() {
    }

    // TODO. 使用自定义注入的Bean对象发送GET请求: 不带参数
    public static String sendGetRequest(RestTemplate restTemplate, String url) {
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
        return response;
    }

    // TODO. 使用自定义注入的Bean对象发送POST请求
    public static String sendPostRequest(RestTemplate restTemplate, String url, Product product)  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = parseJsonBody(product);

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        return restTemplate.postForObject(url, request, String.class);
    }

    // 抛出的InternalServerException异常将被ControllerAdvice处理
    private static String parseJsonBody(Product product) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException exception) {
            throw new InternalServerException("Json Processing Failure");
        }
    }
}
