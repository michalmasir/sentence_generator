package com.gooddata.sentencegenerator.engine.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gooddata.sentencegenerator.engine.dto.WordCategory;
import lombok.Data;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "WORD")
@JsonTypeName("word")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public class Word implements Comparable<Word>{

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORD_ID")
    private Long wordId;

    @Column(name = "WORD")
    private String word;

    @Convert(converter = WordCategoryConverter.class)
    @Column(name = "WORD_CATEGORY")
    private WordCategory wordCategory;

    @JsonBackReference
    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    private Set<SentenceWord> sentenceWords;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;

        if (wordId != null ? !wordId.equals(word1.wordId) : word1.wordId != null) return false;
        if (word != null ? !word.equals(word1.word) : word1.word != null) return false;
        return wordCategory == word1.wordCategory;
    }

    @Override
    public int hashCode() {
        int result = wordId != null ? wordId.hashCode() : 0;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (wordCategory != null ? wordCategory.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Word o) {
        return this.getWordCategory().compareTo(o.getWordCategory());
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordId=" + wordId +
                ", word='" + word + '\'' +
                ", wordCategory=" + wordCategory +
                '}';
    }
}
