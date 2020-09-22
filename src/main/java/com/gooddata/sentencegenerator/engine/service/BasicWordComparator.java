package com.gooddata.sentencegenerator.engine.service;

import com.gooddata.sentencegenerator.engine.jpa.entity.Word;

import java.util.Comparator;

public class BasicWordComparator implements Comparator<Word> {
    public int compare(Word w1, Word w2)
    {
        return Integer.valueOf(w1.getWordCategory().ordinal()).compareTo(Integer.valueOf(w2.getWordCategory().ordinal()));
    }
}
