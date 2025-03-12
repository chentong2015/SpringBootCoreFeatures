package com.spring.tester1.controller;

import com.spring.tester1.ProductHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.anything;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // TODO. 在后端Server没有启动的情况下，Mock请求的发送和返回
    // @MockBean spring-boot-test 3.4.0 版本之后废弃
    @MockitoBean
    private RestTemplate restTemplate;

    @Test
    void testInsertProductWithExceptionWithoutResponseBody() throws Exception {
        Exception expectedException = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Product already exists");
        Mockito.when(restTemplate.postForObject(anything().toString(), anything(), String.class))
                .thenThrow(expectedException);

        mockMvc.perform(post("/products/handler/2")
                        .content(ProductHelper.getRequestBodyContent())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string("Product already exists"));
    }
}