package com.spring.tester1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringBootTester1Config {

    @Bean
    public RestTemplate restTemplate() {
        System.out.println("Inject Rest Template");
        return new RestTemplate();
    }
}