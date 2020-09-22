package com.gooddata.sentencegenerator.engine.jpa.entity;

import com.gooddata.sentencegenerator.engine.dto.WordCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findAll();
    Word findWordByWord(String word);
    Word findWordByWordAndAndWordCategory(String word, WordCategory wordCategory);

    @Query(value = "select * from WORD w where w.WORD_CATEGORY = :wordcategory ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Word getRandomWordByType(@Param("wordcategory") String wordcategory);
}
