package com.gooddata.sentencegenerator.engine.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {
    List<Sentence> findAll();
    Sentence findBySentenceId(Long sentenceId);

    @Query("select s from Sentence s where s.sentenceId = (Select sw1.sentence.sentenceId from SentenceWord sw1 " +
            "join SentenceWord sw2 on sw1.sentence = sw2.sentence " +
            "join SentenceWord sw3 on sw2.sentence = sw3.sentence " +
            "where sw1.word = :word1 " +
            "and sw2.word = :word2 " +
            "and sw3.word = :word3 )")
    Sentence findDuplicate(@Param("word1") Word word1,
                           @Param("word2") Word word2,
                           @Param("word3") Word word3);

    @Query("select s from Sentence s where s.views > 1")
    List<Sentence> findAllDuplicates();
}
