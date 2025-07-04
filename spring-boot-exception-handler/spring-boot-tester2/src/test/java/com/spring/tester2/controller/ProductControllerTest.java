package com.spring.tester2.controller;

import com.spring.tester2.ProductHelper;
import com.spring.tester2.advice.ProductControllerAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    // 使用Autowired会自动添加Controller的ExceptionHandler !!
    @Autowired
    private MockMvc mockMvc;

    // TODO. 使用MockMvcBuilders构建，需要配置自定义的ControllerAdvice
    public ProductControllerTest() {
        MockMvc mockMvc1 = MockMvcBuilders.standaloneSetup(new ProductController())
                .setControllerAdvice(new ProductControllerAdvice())
                .build();
    }

    // TODO. 测试异常的抛出异常后会直接被ExceptionHandler接收并处理，返回特定的body和status
    @Test
    void testInsertProduct() throws Exception {
        mockMvc.perform(post("/products/test/2")
                        .content(ProductHelper.getRequestBodyContent())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("")); // 返回的response body没有任何的数据
    }

    @Test
    void testInsertProductWithException() throws Exception {
        mockMvc.perform(post("/products/test/1")
                        .content(ProductHelper.getRequestBodyContent())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string("Product already exists"));
    }
}