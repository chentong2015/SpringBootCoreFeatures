package com.retail.experience.controller;

import com.retail.experience.ServerApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() {
        try {
            String urlTemplate = "/admin/login/root/21232f297a57a5a743894a0e4a801fc3";
            mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    @Test
    void monitor() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/admin/status")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.numberOfConnectedClients").exists())
                    .andExpect(jsonPath("$.numberOfOrdersWaiting").exists())
                    .andExpect(jsonPath("$.numberOfOrdersProcessing").exists())
                    .andExpect(jsonPath("$.numberOfOrdersDone").exists());
        } catch (Exception exception) {
            System.out.println("Error" + exception.getMessage());
        }
    }
}