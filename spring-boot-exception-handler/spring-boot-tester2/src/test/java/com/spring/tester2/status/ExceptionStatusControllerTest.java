package com.spring.tester2.status;

import com.spring.tester2.status.exception.BadArgumentsException;
import com.spring.tester2.status.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionStatusControllerTest {

    @Autowired
    private MockMvc mvc;

    // TODO. 请求Endpoint抛出的异常自带ResponseStatus状态码
    @Test
    public void givenNotFound_whenGetSpecificException_thenNotFoundCode() throws Exception {
        String exceptionParam = "not_found";
        mvc.perform(get("/exception/{exception_id}", exceptionParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("resource not found",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void givenBadArguments_whenGetSpecificException_thenBadRequest() throws Exception {
        String exceptionParam = "bad_arguments";
        mvc.perform(get("/exception/{exception_id}", exceptionParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result -> assertEquals("bad arguments",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    // 测试正确请求的ResponseEntity<String>结果
    @Test
    public void givenOther_whenGetSpecificException_thenInternalServerError() throws Exception {
        String exceptionParam = "dummy";
        mvc.perform(get("/exception/{exception_id}", exceptionParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("Request OK", result.getResponse().getContentAsString()));
    }
}
