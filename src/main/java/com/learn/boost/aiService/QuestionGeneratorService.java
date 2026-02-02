package com.learn.boost.aiService;


import com.learn.boost.helper.QuestionFileUtil;
import com.learn.boost.model.QuestionFile;
import com.learn.boost.repository.QuestionFileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
public class QuestionGeneratorService {

//    public QuestionFile generateQuestion(
//            String userId,
//            String noteId,
//            String noteText
//    ) throws IOException{
//        System.out.println("Start............\n");
//        String questionText="geminiAIService.generateQuestions(noteText)";
//
//        Path filePath= QuestionFileUtil.saveAsText(
//                userId,noteId,questionText
//        );
//        QuestionFile file = new QuestionFile();
//        file.setUserId(userId);
//        file.setNoteId(noteId);
//        file.setFileName(filePath.getFileName().toString());
//        file.setFilePath(filePath.toString());
//        file.setCreatedAt(LocalDateTime.now());
//        return repository.save(file);
//
//    }
}

