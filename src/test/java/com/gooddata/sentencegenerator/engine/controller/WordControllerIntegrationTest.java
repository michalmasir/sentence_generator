package com.gooddata.sentencegenerator.engine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooddata.sentencegenerator.engine.dto.WordCategory;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    public void getWordsEmptyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/words"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    public void getWordsByTextEmptyTest() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.get("/words"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    public void putWordsEmptyTest() throws Exception {

        Word nWord = new Word();
        nWord.setWordCategory(WordCategory.NOUN);

        Word steveWord = new Word();
        steveWord.setWordCategory(WordCategory.NOUN);
        steveWord.setWord("steve");

        String json = objectMapper.writeValueAsString(nWord);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/words/steve")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String stringResult = result.getResponse().getContentAsString();
        Assert.assertNotNull(stringResult);
        Assert.assertEquals(stringResult , objectMapper.writeValueAsString(steveWord));
    }

    @Test
    @Order(4)
    public void nonExistingWordTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/words/eva"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    public void putAndGetWordsNonEmptyTest() throws Exception {

        Word nWord = new Word();
        nWord.setWordCategory(WordCategory.NOUN);

        Word vWord = new Word();
        vWord.setWordCategory(WordCategory.VERB);


        String nJson = objectMapper.writeValueAsString(nWord);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/words/steve")
                .content(nJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String stringResult1 = result.getResponse().getContentAsString();

        result = mockMvc.perform(MockMvcRequestBuilders.put("/words/steve")
                .content(nJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String stringResult2 = result.getResponse().getContentAsString();
        Assert.assertEquals(stringResult1, stringResult2);

        String sJson = objectMapper.writeValueAsString(vWord);
        result = mockMvc.perform(MockMvcRequestBuilders.put("/words/steve")
                .content(sJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String stringResult3 = result.getResponse().getContentAsString();
        Assert.assertNotNull(stringResult1, stringResult3);
    }
}
