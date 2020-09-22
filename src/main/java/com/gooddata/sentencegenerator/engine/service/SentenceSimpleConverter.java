package com.gooddata.sentencegenerator.engine.service;

import com.gooddata.sentencegenerator.engine.dto.SentenceSimple;
import com.gooddata.sentencegenerator.engine.jpa.entity.Sentence;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SentenceSimpleConverter implements SentenceConverter{
    @Override
    public SentenceSimple convert(Sentence sentence, Comparator comparator){
        List<Word> wordsList = this.toWordList(sentence);
        wordsList.sort(comparator);
        return new SentenceSimple(wordsList.stream().map(w -> w.getWord()).collect(Collectors.joining(" ")));
    }
}
