package com.gooddata.sentencegenerator.engine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooddata.sentencegenerator.engine.dto.WordCategory;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;
import net.javacrumbs.jsonunit.assertj.JsonAssert;
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

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class SentenceControllerNonEmptyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception {
        Word nWord = new Word();
        nWord.setWordCategory(WordCategory.NOUN);

        Word vWord = new Word();
        vWord.setWordCategory(WordCategory.VERB);

        Word aWord = new Word();
        aWord.setWordCategory(WordCategory.ADJECTIVE);


        mockMvc.perform(MockMvcRequestBuilders.put("/words/metal")
                .content(objectMapper.writeValueAsString(nWord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.put("/words/sounds")
                .content(objectMapper.writeValueAsString(vWord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.put("/words/good")
                .content(objectMapper.writeValueAsString(aWord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void generateSentenceTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/sentences/generate"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String stringResult = result.getResponse().getContentAsString();
        Assert.assertNotNull(stringResult);

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/sentences"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String stringResult2 = result2.getResponse().getContentAsString();
        Assert.assertNotNull(stringResult2);
    }

    @Test
    public void getSentencesByIdNonEmptyTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/sentences/generate"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/sentences/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String stringResult = result.getResponse().getContentAsString();
        Assert.assertNotNull(stringResult);
    }

    @Test
    public void getSentencesByIdYodaNonEmptyTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/sentences/generate"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/sentences/1/yodaTalk"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String stringResult = result.getResponse().getContentAsString();
        Assert.assertNotNull(stringResult);
    }

    @Test
    public void badRequestTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/sentences/generate"))
                .andExpect(status().isBadRequest());
    }

}
