package com.spring.tester1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringBootTester1Config {

    // 注入的Bean可以在测试时进行Mock
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
