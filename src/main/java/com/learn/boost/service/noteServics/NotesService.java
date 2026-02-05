package com.learn.boost.service.noteServics;

import com.learn.boost.dto.NoteRequestDto;
import com.learn.boost.dto.NoteResponseDto;
import com.learn.boost.exception.NoteNotFoundException;
import com.learn.boost.exception.UserNotFoundException;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
    private void isValidFile(MultipartFile file) throws IllegalArgumentException {
        if(file==null || file.isEmpty()){
            throw new IllegalArgumentException("file must not be empty");
        }
        if(!file.getOriginalFilename().endsWith(".txt")){
            throw new IllegalArgumentException("must be .txt file");
        }
    }
    private User findUser(String userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user is not found"));
    }
    private String saveFile(MultipartFile file,String noteId){
        try {
            String fileName=noteId+".txt";
            Path path  = Paths.get(DIR).resolve(fileName);
            Files.copy(
                    file.getInputStream(),
                    path,
                    StandardCopyOption.REPLACE_EXISTING
            );
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("faild to store file exception is: -> "+e.toString());
        }
    }

    public String createNote(MultipartFile file, NoteRequestDto dto,String userId) throws UserNotFoundException, IllegalAccessException {
        isValidFile(file);
        User user = findUser(userId);
        Notes notes =NoteMapper.dtoToNote(dto);
        notes.setUser(user);
        notesRepository.save(notes);
        try {
            String filePath = saveFile(file,notes.getNotesId());
            notes.setNotes_path(filePath);
            notesRepository.save(notes);
        }catch (Exception ex){
            notesRepository.deleteById(notes.getNotesId());
            throw new RuntimeException(
                    "Failed to upload file and rollilng back",ex
            );
        }
        return "Note created succesfully";
    }
    public List<NoteResponseDto> getNotesDtoByUserId(String userid) {
        List<Notes> userNotes= notesRepository.findByUser_UserId(userid).stream().toList();
         return userNotes.stream().map(NoteMapper::noteToDto).toList();
    }
    public Resource sendTextFile(String noteId) throws NoteNotFoundException {
        Notes note= notesRepository.findById(noteId).orElseThrow(()->new NoteNotFoundException(noteId));
        String path=note.getNotes_path();
        Resource resource = new PathResource(path);
        return resource;
    }

}
