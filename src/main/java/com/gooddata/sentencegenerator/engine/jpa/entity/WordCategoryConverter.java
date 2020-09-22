package com.gooddata.sentencegenerator.engine.jpa.entity;

import com.gooddata.sentencegenerator.engine.dto.WordCategory;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class WordCategoryConverter implements AttributeConverter<WordCategory, String> {
    @Override
    public String convertToDatabaseColumn(WordCategory wordCategory) {
        if(wordCategory == null){
            return null;
        }
        return wordCategory.getLabel();
    }

    @Override
    public WordCategory convertToEntityAttribute(String s) {
        if(s == null){
            return null;
        }
        return Stream.of(WordCategory.values())
                .filter(wc -> wc.getLabel().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
