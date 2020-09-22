package com.gooddata.sentencegenerator.engine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("word")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public class WordDto {
    @JsonIgnore
    private Long wordId;
    private String word;
    private WordCategory wordCategory;

    public WordDto() {
    }

    public WordDto(Long wordId, String word, WordCategory wordCategory) {
        this.wordId = wordId;
        this.word = word;
        this.wordCategory = wordCategory;
    }
}
