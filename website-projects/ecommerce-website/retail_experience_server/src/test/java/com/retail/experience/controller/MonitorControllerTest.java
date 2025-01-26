package com.retail.experience.controller;

import com.retail.experience.ServerApplication;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
class MonitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get_order_status() {
        try {
            String urlTemplate = "/monitor/order/status/test_order_id";
            mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderId").value(IsNull.nullValue()));
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}