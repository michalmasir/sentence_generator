package com.gooddata.sentencegenerator.engine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooddata.sentencegenerator.engine.dto.WordCategory;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class SentenceControllerHalfEmptyIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception {
        Word nWord = new Word();
        nWord.setWordCategory(WordCategory.NOUN);

        Word vWord = new Word();
        vWord.setWordCategory(WordCategory.VERB);

        Word secondVWord = new Word();
        secondVWord.setWordCategory(WordCategory.VERB);

        mockMvc.perform(MockMvcRequestBuilders.put("/words/metal")
                .content(objectMapper.writeValueAsString(nWord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.put("/words/sounds")
                .content(objectMapper.writeValueAsString(vWord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.put("/words/codes")
                .content(objectMapper.writeValueAsString(secondVWord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void notEnoughWordsToGenerateSentenceTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sentences/generate"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void notEnoughWordsSentencesTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sentences"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void notEnoughWordsgetSentece() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sentences/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void notEnoughWordsgetYodaSentece() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sentences/1/yoda"))
                .andExpect(status().isNotFound());
    }

}
