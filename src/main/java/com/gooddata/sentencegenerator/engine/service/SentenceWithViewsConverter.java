package com.gooddata.sentencegenerator.engine.service;

import com.gooddata.sentencegenerator.engine.dto.SentenceWithViews;
import com.gooddata.sentencegenerator.engine.jpa.entity.Sentence;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;
import javassist.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SentenceWithViewsConverter implements SentenceConverter{
    @Override
    public SentenceWithViews convert(Sentence sentence, Comparator comparator){
        List<Word> wordsList = this.toWordList(sentence);
        wordsList.sort(comparator);
        return new SentenceWithViews(wordsList.stream().map(w -> w.getWord()).collect(Collectors.joining(" ")), sentence.getViews());
    }
}
