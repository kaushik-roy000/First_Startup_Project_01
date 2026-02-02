package com.learn.boost.service;

import com.learn.boost.dto.NoteResponseDto;
import com.learn.boost.mapper.NoteMapper;
import com.learn.boost.model.Notes;
import com.learn.boost.model.User;
import com.learn.boost.repository.NotesRepository;
import com.learn.boost.repository.UserRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NotesService {
    private UserRepository userRepository;
    private NotesRepository notesRepository;

    public NotesService(NotesRepository notesRepository, UserRepository userRepository) {
        this.notesRepository = notesRepository;
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
        notesRepository.save(notes);


        return "notes upload successfully";

    }


    public String getNotesByUserId(String userid) {
        return null;
    }

    public List<NoteResponseDto> getNotesDtoByUserId(String userid) {
        List<Notes> userNotes= notesRepository.findByUser_UserId(userid).stream().toList();
         return userNotes.stream().map(NoteMapper::noteToDto).toList();
    }
    public Resource sendTextFile(String noteId){
        Notes note= notesRepository.findById(noteId).orElseThrow(()->new RuntimeException("notes not found"));
        String path=note.getNotes_path();
        Resource resource = new PathResource(path);
        return resource;
    }
}
