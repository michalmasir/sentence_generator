package com.gooddata.sentencegenerator.engine.service;

import com.gooddata.sentencegenerator.engine.dto.SentenceSimple;
import com.gooddata.sentencegenerator.engine.dto.SentenceWithViews;
import com.gooddata.sentencegenerator.engine.dto.WordCategory;
import com.gooddata.sentencegenerator.engine.exception.NotFoundException;
import com.gooddata.sentencegenerator.engine.jpa.entity.*;
import com.gooddata.sentencegenerator.engine.service.SentenceService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
public class SentenceServiceTest {

    @Autowired
    SentenceService sentenceService;
    @MockBean
    SentenceRepository sentenceRepository;
    @MockBean
    WordRepository wordRepository;

    private Word nWord;
    private Word vWord;
    private Word aWord;
    private Word someWord;

    @TestConfiguration
    static class WordServiceTestTestConfig {

        @Bean
        public SentenceService sentenceService() {
            return new SentenceService();
        }
    }

    @BeforeEach
    public void setUp() {
        nWord = new Word();
        nWord.setWordCategory(WordCategory.NOUN);
        nWord.setWord("metal");

        vWord = new Word();
        vWord.setWordCategory(WordCategory.VERB);
        vWord.setWord("sounds");

        aWord = new Word();
        aWord.setWordCategory(WordCategory.ADJECTIVE);
        aWord.setWord("good");

        someWord = new Word();
        someWord.setWordCategory(WordCategory.ADJECTIVE);
        someWord.setWord("best");

        Mockito.when(wordRepository.getRandomWordByType(nWord.getWordCategory().getLabel()))
                .thenReturn(nWord);
        Mockito.when(wordRepository.getRandomWordByType(vWord.getWordCategory().getLabel()))
                .thenReturn(vWord);
        Mockito.when(wordRepository.getRandomWordByType(aWord.getWordCategory().getLabel()))
                .thenReturn(aWord);

    }

    @Test
    public void generateDistinctSentenceTest() {
        Mockito.when(sentenceRepository.findDuplicate(Mockito.any(Word.class), Mockito.any(Word.class), Mockito.any(Word.class)))
                .thenReturn(null);
        var sentence = sentenceService.generate();
        Assert.assertNotNull(sentence);
        Assert.assertTrue(sentence.getViews() == 1);
        Assert.assertTrue(sentence.getSentenceWords().size() == 3);
        Assertions.assertThat(sentence.getSentenceWords().stream().map(m -> m.getWord()).collect(Collectors.toList())).containsExactlyInAnyOrder(nWord, vWord, aWord);
    }

    @Test
    public void generateDuplicateSentenceTest() {
        Sentence s = new Sentence(1, new Date(), List.of(nWord, vWord, aWord));
        Mockito.when(sentenceRepository.findDuplicate(Mockito.any(Word.class), Mockito.any(Word.class), Mockito.any(Word.class)))
                .thenReturn(s);

        var sentence = sentenceService.generate();
        Assert.assertTrue(sentence.getViews() == 2);
    }

    @Test
    public void allSentencesTest() {
        Sentence sentence1 = new Sentence(1, new Date(), List.of(nWord, vWord, aWord));
        Sentence sentence2 = new Sentence(1, new Date(), List.of(nWord, vWord, someWord));

        Mockito.when(sentenceRepository.findAll())
                .thenReturn(List.of(sentence1, sentence2));
        List<SentenceSimple> sentences = sentenceService.getAllSentences();
        Assert.assertTrue(sentences.size() == 2);
    }

    @Test
    public void getSentenceByIdTest() {
        Sentence sentence1 = new Sentence(1, new Date(), List.of(nWord, vWord, aWord));
        sentence1.setSentenceId(1L);

        Mockito.when(sentenceRepository.findBySentenceId(1L))
                .thenReturn(sentence1);

        SentenceWithViews sentence = sentenceService.getSentenceById(1L);
        Assert.assertThrows(NotFoundException.class, () -> {
            sentenceService.getSentenceById(2L);
        });
        //also checks order
        Assert.assertEquals(sentence.getText(), nWord.getWord() + " " + vWord.getWord() + " " + aWord.getWord());
    }

    @Test void getYodaSentence(){
        Sentence sentence1 = new Sentence(1, new Date(), List.of(nWord, vWord, aWord));
        sentence1.setSentenceId(1L);

        Mockito.when(sentenceRepository.findBySentenceId(1L))
                .thenReturn(sentence1);

        SentenceSimple sentence = sentenceService.getYodaSentence(1L);
        Assert.assertThrows(NotFoundException.class, () -> {
            sentenceService.getSentenceById(2L);
        });
        //also checks YODA order
        Assert.assertEquals(sentence.getText(), aWord.getWord() + " " + nWord.getWord() + " " + vWord.getWord());

    }


}
