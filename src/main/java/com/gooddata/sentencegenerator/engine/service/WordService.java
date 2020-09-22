package com.gooddata.sentencegenerator.engine.service;

import com.gooddata.sentencegenerator.engine.dto.WordDto;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;
import com.gooddata.sentencegenerator.engine.jpa.entity.WordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WordService {
    @Autowired
    WordRepository wordRepository;

    @Transactional
    public Word createOrUpdateWord(String word, WordDto wordDto){
        Word storedWord = wordRepository.findWordByWord(word);
        if(storedWord != null && storedWord.getWordCategory() != wordDto.getWordCategory()){
            storedWord.setWordCategory(wordDto.getWordCategory());
            wordRepository.save(storedWord);
            return storedWord;
        }else if(storedWord == null){
            Word newWord = new Word();
            newWord.setWord(word);
            newWord.setWordCategory(wordDto.getWordCategory());
            wordRepository.save(newWord);
            return newWord;
        }else{
            return storedWord;
        }
    }
}
