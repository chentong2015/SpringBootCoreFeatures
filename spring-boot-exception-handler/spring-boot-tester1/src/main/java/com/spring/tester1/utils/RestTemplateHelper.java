package com.spring.tester1.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static String sendPostRequest(RestTemplate restTemplate, String url, Product product) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(product);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        String responseBody = restTemplate.postForObject(url, request, String.class);
        System.out.println(responseBody);
        return responseBody;
    }
}
