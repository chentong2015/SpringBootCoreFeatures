package com.spring.tester1.controller;

import com.spring.tester1.ProductHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // TODO. Mock注入到SpringContext中的RestTemplate Bean
    @MockitoBean
    private RestTemplate restTemplate;

    @Test
    void testInsertProductWithExceptionWithoutResponseBody() throws Exception {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(400); // BAD_REQUEST
        RuntimeException exception = new HttpClientErrorException(statusCode, "Product already exists");

        // Mock RestTemplate的请求方法: 无需启动后端test2应用
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.any())).thenThrow(exception);

        mockMvc.perform(post("/products/handler/2")
                        .content(ProductHelper.getRequestBodyContent())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string("400 Product already exists"));
    }
}