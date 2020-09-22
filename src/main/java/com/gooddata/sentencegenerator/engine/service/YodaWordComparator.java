package com.gooddata.sentencegenerator.engine.service;

import com.gooddata.sentencegenerator.engine.dto.WordCategory;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class YodaWordComparator implements Comparator<Word> {
    public int compare(Word w1, Word w2)
    {
        List<WordCategory> yodaOrderedList = List.of(WordCategory.ADJECTIVE, WordCategory.NOUN, WordCategory.VERB);
        return Integer.valueOf(yodaOrderedList.indexOf(w1.getWordCategory())).compareTo(Integer.valueOf(yodaOrderedList.indexOf(w2.getWordCategory())));
    }
}
