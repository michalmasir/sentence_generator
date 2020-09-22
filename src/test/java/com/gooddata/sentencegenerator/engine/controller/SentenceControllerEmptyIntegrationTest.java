package com.gooddata.sentencegenerator.engine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class SentenceControllerEmptyIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getSentencesEmptyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sentences"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSenteceWhenEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sentences/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getYodaSenteceWhenEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sentences/1/yoda"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void generateSenteceWhenEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sentences/generate"))
                .andExpect(status().isNotFound());
    }
}
