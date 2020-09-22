package com.gooddata.sentencegenerator.engine.service;

import com.gooddata.sentencegenerator.engine.dto.SentenceSimple;
import com.gooddata.sentencegenerator.engine.jpa.entity.Sentence;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;
import javassist.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface SentenceConverter {
    <T> T convert(Sentence sentence, Comparator comparator);
    default List<Word> toWordList(Sentence sentence){
        return sentence.getSentenceWords().stream()
                .map(sw -> sw.getWord())
                .collect(Collectors.toList());
    }
}
