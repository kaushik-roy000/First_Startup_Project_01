package com.learn.boost.controller;
import com.learn.boost.service.questionsServies.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/ask")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    //ask a question and get the answer
    @GetMapping("/{userId}")
    public ResponseEntity<UrlResource> askQuestion(
            @PathVariable String userId,
            @RequestParam String question
    ) throws IOException {
        return ResponseEntity.ok(questionService.saveQuestionAndAnswer(userId,question));

    }
}
