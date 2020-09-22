package com.gooddata.sentencegenerator.engine.service;

import com.gooddata.sentencegenerator.engine.dto.SentenceSimple;
import com.gooddata.sentencegenerator.engine.dto.SentenceWithViews;
import com.gooddata.sentencegenerator.engine.exception.NotFoundException;
import com.gooddata.sentencegenerator.engine.jpa.entity.*;
import com.gooddata.sentencegenerator.engine.dto.WordCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SentenceService {

    @Autowired
    private SentenceRepository sentenceRepository;
    @Autowired
    private WordRepository wordRepository;

    private SentenceSimpleConverter sentenceSimpleConverter = new SentenceSimpleConverter();
    private SentenceWithViewsConverter sentenceWithViewsConverter  = new SentenceWithViewsConverter();

    @Transactional
    public Sentence generate() {
        List<Word> wordList = new ArrayList<>();
        for (WordCategory cat : WordCategory.values()) {
            Word word = wordRepository.getRandomWordByType(cat.getLabel());
            if (word != null) {
                wordList.add(word);
            } else {
                throw new NotFoundException("Not enough words in system.");
            }
        }

        Sentence sentence = sentenceRepository.findDuplicate(wordList.get(0), wordList.get(1), wordList.get(2));

        if (sentence == null) {
            sentence = new Sentence(1, new Date(), wordList);
        } else {
            sentence.setViews(sentence.getViews() + 1);
        }
        sentenceRepository.save(sentence);
        return sentence;
    }

    public List<SentenceSimple> getAllSentences()  {
        List<Sentence> sentences = sentenceRepository.findAll();
        if(sentences.isEmpty()){
            throw new NotFoundException("Sentence", null);
        }
        return sentences.stream().map(s -> sentenceSimpleConverter.convert(s, new BasicWordComparator())).collect(Collectors.toList());
    }

    public SentenceWithViews getSentenceById(Long id)  {
        Sentence sentence = sentenceRepository.findBySentenceId(id);
        if(sentence == null){
            throw new NotFoundException("Sentence", null);
        }
        return sentenceWithViewsConverter.convert(sentence, new BasicWordComparator());
    }

    public SentenceSimple getYodaSentence(Long id)  {
        Sentence sentence = sentenceRepository.findBySentenceId(id);
        if(sentence == null){
            throw new NotFoundException("Sentence", null);
        }
        return sentenceSimpleConverter.convert(sentence, new YodaWordComparator());
    }

    public List<SentenceWithViews> getAllDuplicates() {
        List<Sentence> sentences = sentenceRepository.findAllDuplicates();
        if(sentences.isEmpty()){
            throw new NotFoundException("Sentence", null);
        }
        return sentences.stream().map(s -> sentenceWithViewsConverter.convert(s, new BasicWordComparator())).collect(Collectors.toList());

    }
}
