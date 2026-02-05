package com.learn.boost.controller;



import com.learn.boost.service.aiService.OpenRouterService;
import com.learn.boost.mapper.MCQResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenRouterController {

    private final OpenRouterService service;

    public OpenRouterController(OpenRouterService service) {
        this.service = service;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String q) {
        return service.askQuestion(q);
    }
    @GetMapping("/generate")
    public String generateQA(@RequestParam String topic) {
        return service.generateQuestionAnswer(topic);
    }

    @GetMapping("/generate-mcq")
    public String generateMCQ(@RequestParam String paragraph) {
        return service.generateMCQs(paragraph);
    }

    @GetMapping("/generate-mcq-json")
    public MCQResponse generateMCQJson(@RequestParam String paragraph) {
        return service.generateMCQJson(paragraph);
    }


}
