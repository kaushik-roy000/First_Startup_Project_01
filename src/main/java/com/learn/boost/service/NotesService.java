package com.learn.boost.service;

import com.learn.boost.model.Notes;
import com.learn.boost.model.User;
import com.learn.boost.repository.NodeRepository;
import com.learn.boost.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class NotesService {
    private UserRepository userRepository;
    private NodeRepository noteRepository;

    public NotesService(NodeRepository noteRepository,UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository=userRepository;
    }
    @Value("${file.text}")
    String DIR;

    @PostConstruct
    public void createNoteFolder(){

//        File file=new File(DIR);

        try{
            Files.createDirectories(Paths.get(DIR));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Notes createNote(String noteName,String noteTitle,int Rating){
        Notes notes=new Notes();
        notes.setNotesId(UUID.randomUUID().toString());
        notes.setNotes_name(noteName);
        notes.setTitle(noteTitle);
        notes.setRating(Rating%5);
        return notes;
    }

    public String saveNotesWithPaths(String userId, Notes notes, MultipartFile txtFile) throws IOException{
        if(txtFile.isEmpty()) return "file is not exist or empty";
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
        String fileName=notes.getNotesId()+".txt";

        Path path=Paths.get(DIR,fileName);
        System.out.println(path.toString());
        try(InputStream inputStream=txtFile.getInputStream())
        {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        notes.setNotes_path(path.toString());
        notes.setUser(user);
        noteRepository.save(notes);


        return "notes upload successfully";

    }


}
