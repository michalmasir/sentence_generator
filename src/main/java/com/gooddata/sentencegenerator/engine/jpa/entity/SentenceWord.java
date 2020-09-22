package com.gooddata.sentencegenerator.engine.jpa.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SENTENCE_WORD")
public class SentenceWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SENTENCE_WORD_ID")
    private Long sentenceWordId;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "sentenceId")
    private Sentence sentence;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "wordId")
    private Word word;

    @Column(name = "POSITION")
    private Integer position;

    public SentenceWord(Word word, Sentence sentence, Integer position) {
        this.word = word;
        this.sentence = sentence;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SentenceWord that = (SentenceWord) o;

        if (sentenceWordId != null ? !sentenceWordId.equals(that.sentenceWordId) : that.sentenceWordId != null)
            return false;
        if (sentence != null ? !sentence.equals(that.sentence) : that.sentence != null) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;
        return position != null ? position.equals(that.position) : that.position == null;
    }

    @Override
    public int hashCode() {
        int result = sentenceWordId != null ? sentenceWordId.hashCode() : 0;
        result = 31 * result + (sentence != null && sentence.getAddetDt() != null ? sentence.getAddetDt().hashCode() : 0);
        result = 31 * result + (word != null && word.getWord() != null ? word.getWord().hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
