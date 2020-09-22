package com.gooddata.sentencegenerator.engine.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gooddata.sentencegenerator.engine.dto.SentenceSimple;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "SENTENCE")
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SENTENCE_ID")
    private Long sentenceId;

    @Column(name = "VIEWS")
    private Integer views;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDET_DT")
    private Date addetDt;

    @JsonBackReference
    @OneToMany(mappedBy = "sentence", cascade = CascadeType.ALL)
    private Set<SentenceWord> sentenceWords;

    public Sentence(Integer views, Date addetDt, List<Word> wordList) {
        this.views = views;
        this.addetDt = addetDt;
        AtomicInteger counter = new AtomicInteger(0);
        this.sentenceWords = wordList.stream().map(w -> new SentenceWord(w, this, counter.getAndIncrement())).collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence = (Sentence) o;

        if (sentenceId != null ? !sentenceId.equals(sentence.sentenceId) : sentence.sentenceId != null) return false;
        if (views != null ? !views.equals(sentence.views) : sentence.views != null) return false;
        return addetDt != null ? addetDt.equals(sentence.addetDt) : sentence.addetDt == null;
    }

    @Override
    public int hashCode() {
        int result = sentenceId != null ? sentenceId.hashCode() : 0;
        result = 31 * result + (views != null ? views.hashCode() : 0);
        result = 31 * result + (addetDt != null ? addetDt.hashCode() : 0);
        return result;
    }
}
