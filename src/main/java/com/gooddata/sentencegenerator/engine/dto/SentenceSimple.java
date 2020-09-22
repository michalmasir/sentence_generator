package com.gooddata.sentencegenerator.engine.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonTypeName("sentence")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public class SentenceSimple {
    private String text;

    public SentenceSimple(String text) {
        this.text = text;
    }
}
