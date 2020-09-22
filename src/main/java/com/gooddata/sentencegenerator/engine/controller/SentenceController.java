package com.gooddata.sentencegenerator.engine.controller;

import com.gooddata.sentencegenerator.engine.dto.SentenceWithViews;
import com.gooddata.sentencegenerator.engine.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sentences")
public class SentenceController {
    @Autowired
    private SentenceService sentenceService;

    @GetMapping("")
    public ResponseEntity getSentences(){
        return new ResponseEntity(sentenceService.getAllSentences(), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity generateSentence(){
        return new ResponseEntity(sentenceService.generate(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getSentence(@PathVariable Long id){
        var sentence = sentenceService.getSentenceById(id);
        return sentence != null ? new ResponseEntity(sentence, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/yodaTalk")
    public ResponseEntity getSentenceYodaStyle(@PathVariable Long id){
        var sentence = sentenceService.getYodaSentence(id);
        return sentence != null ? new ResponseEntity(sentence, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/duplicates")
    public ResponseEntity getAllDuplicates(){
        var sentences = sentenceService.getAllDuplicates();
        return new ResponseEntity(sentences, HttpStatus.OK);
    }
}
