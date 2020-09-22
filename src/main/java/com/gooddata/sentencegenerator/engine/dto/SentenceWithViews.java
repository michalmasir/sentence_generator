package com.gooddata.sentencegenerator.engine.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("sentence")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public class SentenceWithViews extends SentenceSimple {
    private int showDisplayCount;

    public SentenceWithViews(String text, int showDisplayCount) {
        super(text);
        this.showDisplayCount = showDisplayCount;
    }
}
