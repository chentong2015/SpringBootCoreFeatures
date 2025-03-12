package com.spring.tester1.controller;

import com.spring.tester1.ProductHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testInsertProductWithFeignException() throws Exception {

        mockMvc.perform(post("/products/test/2")
                        .content(ProductHelper.getRequestBodyContent())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string("Product already exists"));
    }

    // 报错 org.springframework.web.util.NestedServletException: Request processing failed;
    // 后端的server没有启动，所以无法测试
    @Test
    void testInsertProductWithExceptionAndResponseBody() throws Exception {

        mockMvc.perform(post("/products/test/2")
                        .content(ProductHelper.getRequestBodyContent())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string("Product already exists"));
    }

    // 补充测试覆盖率，设置返回的ResponseEntity的另一个条件
    @Test
    void testInsertProductWithExceptionWithoutResponseBody() throws Exception {

        mockMvc.perform(post("/products/test/2")
                        .content(ProductHelper.getRequestBodyContent())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string("error: without response body"));
    }
}