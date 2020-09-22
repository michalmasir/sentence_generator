package com.gooddata.sentencegenerator.engine.controller;

import com.gooddata.sentencegenerator.engine.dto.WordCategory;
import com.gooddata.sentencegenerator.engine.dto.WordDto;
import com.gooddata.sentencegenerator.engine.jpa.entity.Word;
import com.gooddata.sentencegenerator.engine.jpa.entity.WordRepository;
import com.gooddata.sentencegenerator.engine.service.WordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/words")
public class WordController {

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WordService wordService;

    @GetMapping("")
    public ResponseEntity getWords(){
        List<Word> words = wordRepository.findAll();
        if(words.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            List<WordDto> dtos = words.stream().map(w -> modelMapper.map(w,WordDto.class)).collect(Collectors.toList());
            return new ResponseEntity(dtos, HttpStatus.OK);
        }
    }

    @GetMapping("/{word}")
    public ResponseEntity getWordsByText(@PathVariable String word){
        var wordResult = wordRepository.findWordByWord(word);
        if(wordResult == null){
           return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity(modelMapper.map(wordResult, WordDto.class), HttpStatus.OK);
        }
    }

    @PutMapping("/{word}")
    public ResponseEntity putWords(@RequestBody WordDto wordPayload, @PathVariable String word){
        //todo pridat kontrolu zo suboru
        if(wordPayload != null && wordPayload.getWordCategory() != null && wordPayload.getWordCategory().getClass() == WordCategory.class){
            return new ResponseEntity(wordService.createOrUpdateWord(word, wordPayload), HttpStatus.OK);
        }else{
            return new ResponseEntity (HttpStatus.BAD_REQUEST);
        }
    }


}
