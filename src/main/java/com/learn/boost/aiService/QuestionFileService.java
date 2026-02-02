package com.learn.boost.aiService;


import com.learn.boost.mapper.MCQResponse;
import com.learn.boost.model.Notes;
import com.learn.boost.model.QuestionFile;
import com.learn.boost.repository.NotesRepository;
import com.learn.boost.repository.QuestionFileRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionFileService {

    @Autowired
    private OpenRouterService openRouterService;
    @Value("${file.questions}")
    private String QUESTION_DIR;
    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private QuestionFileRepository questionFileRepository;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(QUESTION_DIR));
    }
//          public MCQResponse generateQuestion(String noteId) {
//        Notes note=notesRepository.findById(noteId).orElseThrow(
//                ()->new RuntimeException("notes not found in db")
//        );
//        String notePath = note.getNotes_path();
//        String noteText ;
//        return null;
//
//    }
    public void saveMcq(String userId, String noteId) {

        //save also in database + also save txt in stroge
        QuestionFile question = new QuestionFile();

        Notes note = notesRepository.findById(noteId).orElseThrow(
                () -> new RuntimeException("notes not found in db")
        );
        Path notePath= Paths.get(note.getNotes_path());
        System.out.println(notePath.toString());
        String noteText = "";
        try {
            noteText = Files.readString(notePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read note file", e);
        }

        String mcqResponse = openRouterService.generateMCQs(noteText);

        Path userDir = Paths.get(QUESTION_DIR, userId);
        try {
            Files.createDirectories(userDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create user directory", e);
        }

        Path mcqFilePath = userDir.resolve(noteId + "_mcq.txt");

        try {
            Files.writeString(mcqFilePath, mcqResponse, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save MCQ file", e);
        }
        question.setNoteId(note.getNotesId());
        question.setFileName(note.getNotes_name());
        question.setUserId(userId);
        question.setCreatedAt(LocalDateTime.now());
        question.setFilePath(mcqFilePath.toString());
        questionFileRepository.save(question);

        System.out.println("MCQ saved at: " + mcqFilePath);

    }
    public Path findPath(QuestionFile questionFile){
        return Paths.get( questionFile.getFilePath());
    }
    public Resource texfile(Path path){
        return new FileSystemResource(path);
        //return new UrlResource(path.toUri());
    }
    public List<String> getAllMcqPathByuserId(String userId) {
        List<QuestionFile> questionFileList=questionFileRepository.findByUserId(userId);
       // List<String> questionsPath = questionFileList.stream().map(questionFile ->  findPath(questionFile)).toString();

        return null;
//        Resource resource=questionsPath;
//
//        List<Resource> resourceList=new ArrayList<>();
//        for(Path path:questionsPath){
//            resourceList.add(texfile(path));
//        }
//
//        return null;
        //List<Resource> =
    }

    public Resource getAMcqByNoteIdbyuserId(String userId, String noteId) {
        QuestionFile questionFile = questionFileRepository
                .findByNoteId(noteId)
                .orElseThrow(() ->
                        new RuntimeException("MCQ not found for noteId:" + noteId)
                );
        if (!questionFile.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access  to MCQ file");
        }
        Path path = Paths.get(questionFile.getFilePath());

        if (!Files.exists(path)) {
            throw new RuntimeException("MCQ file not found on disk");
        }

        return new FileSystemResource(path);
    }
}

