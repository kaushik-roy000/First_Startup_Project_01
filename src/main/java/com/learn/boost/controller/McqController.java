package com.learn.boost.controller;

import com.learn.boost.service.aiService.QuestionFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class McqController {

    @Autowired
    private QuestionFileService questionFileService;


    @GetMapping("/generate_mcq/{userId}/{noteId}")
    public ResponseEntity<String> generateMcq(
            @PathVariable String userId,
            @PathVariable String noteId
    ){
        //questionFileService.generateQuestion(userId,noteId);
        questionFileService.saveMcq(userId,noteId);


        return ResponseEntity.ok("Generated Successfully");
    }

    @GetMapping("/mcq/{userid}/{noteId}")//get a  note all mcq
    public ResponseEntity<Resource> getAllMcqByuserId(
            @PathVariable String userid,
            @PathVariable String noteId
    ){
        Resource resource = questionFileService.getAMcqByNoteIdbyuserId(userid, noteId);
        return ResponseEntity.ok(  resource);
    }
    //
}
