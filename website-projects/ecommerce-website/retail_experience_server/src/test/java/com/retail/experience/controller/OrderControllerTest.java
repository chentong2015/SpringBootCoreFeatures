package com.retail.experience.controller;

import base.model.request.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.experience.ServerApplication;
import com.retail.experience.helper.OrderHelper;
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
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void list_cpus() {
        list_components("/order/list/cpu");
    }

    @Test
    void list_gpus() {
        list_components("/order/list/gpu");
    }

    @Test
    void list_keyboard() {
        list_components("/order/list/keyboard");
    }

    @Test
    void list_memory() {
        list_components("/order/list/memory");
    }

    @Test
    void list_monitor() {
        list_components("/order/list/monitor");
    }

    @Test
    void list_mouse() {
        list_components("/order/list/mouse");
    }

    @Test
    void list_storage() {
        list_components("/order/list/storage");
    }

    void list_components(String urlTemplate) {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isNotEmpty());
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    @Test
    void order_computer() throws InterruptedException {
        Thread[] threads = new Thread[20];
        for (int index = 0; index < 20; index++) {
            threads[index] = new Thread(this::test_order_computer);
            threads[index].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    void test_order_computer() {
        try {
            OrderRequest order = OrderHelper.generateSameOrderRequest();
            String orderId = order.getOrderId();
            String json = new ObjectMapper().writeValueAsString(order);
            mockMvc.perform(MockMvcRequestBuilders.post("/order/computer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderId").value(orderId))
                    .andExpect(jsonPath("$.orderStatus").value("true"));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}