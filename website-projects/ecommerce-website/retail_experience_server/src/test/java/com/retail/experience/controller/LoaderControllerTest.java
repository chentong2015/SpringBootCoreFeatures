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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
class LoaderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void load_resources_data() {
        try {
            String urlTemplate = "/load/resources?filepath=Inventory_extra.csv";
            mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    @Test
    void load_resources_data_failed() {
        try {
            String urlTemplate = "/load/resources?filepath=Inventory_test.csv";
            mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}