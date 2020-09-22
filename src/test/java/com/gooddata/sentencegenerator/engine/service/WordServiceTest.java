package com.gooddata.sentencegenerator.engine.service;

import com.gooddata.sentencegenerator.engine.dto.WordCategory;
import com.gooddata.sentencegenerator.engine.dto.WordDto;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;
import com.gooddata.sentencegenerator.engine.jpa.entity.WordRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
public class WordServiceTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public WordService wordService() {
            return new WordService();
        }
    }

    @MockBean
    private WordRepository wordRepository;
    @Autowired
    private WordService wordService;

    @BeforeEach
    public void setUp() {
        Word nWord = new Word();
        nWord.setWordId(1L);
        nWord.setWordCategory(WordCategory.ADJECTIVE);
        nWord.setWord("metal");

        Word vWord = new Word();
        vWord.setWordId(2L);
        vWord.setWordCategory(WordCategory.VERB);
        vWord.setWord("thirdWord");

        Mockito.when(wordRepository.findWordByWord("metal"))
                .thenReturn(nWord);
        Mockito.when(wordRepository.findWordByWord("thirdWord"))
                .thenReturn(vWord);
    }

    @Test
    public void createWordTest(){
        final String text = "metal";
        Word result = wordService.createOrUpdateWord(text, new WordDto(null, null, WordCategory.ADJECTIVE));
        Assert.assertEquals(result.getWord(), text);
        Assert.assertEquals(result.getWordCategory(), WordCategory.ADJECTIVE);
    }

    @Test
    public void updateWordTest(){
        final String text = "metal";
        Word result = wordService.createOrUpdateWord(text, new WordDto(null, null, WordCategory.ADJECTIVE));
        Word result2 = wordService.createOrUpdateWord(text, new WordDto(null, null, WordCategory.VERB));
        Assert.assertEquals(result.getWord(), result2.getWord());
        Assert.assertNotEquals(result.getWordCategory(), result2.getWordCategory());
    }

}
