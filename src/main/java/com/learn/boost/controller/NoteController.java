package com.learn.boost.controller;

import com.learn.boost.dto.NoteRequestDto;
import com.learn.boost.dto.NoteResponseDto;
import com.learn.boost.exception.NoteNotFoundException;
import com.learn.boost.exception.UserNotFoundException;
import com.learn.boost.service.noteServics.NotesService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NoteController {
    private NotesService notesService;

    public NoteController(NotesService notesService) {
        this.notesService = notesService;
    }

    //Upload note controller sending noteRequestdto and file

    @PostMapping(
            value = "/upload-note/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadTextFile(@RequestPart("file") MultipartFile file,
                                                 @ModelAttribute NoteRequestDto noteRequestDto,
                                                 @PathVariable String userId) throws UserNotFoundException, IllegalAccessException {
        String response = notesService.createNote(file,noteRequestDto,userId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/notes/{userid}")
    public ResponseEntity<List<NoteResponseDto>> getNotes(
            @PathVariable String userid
    ){

        List<NoteResponseDto> response=notesService.getNotesDtoByUserId(userid);
        return ResponseEntity.ok(response);
    }
    //send the text in note specific note
    @GetMapping("/note/{userId}/{noteId}")
    public ResponseEntity<Resource> getUserNote(
            @PathVariable String userId,
            @PathVariable String noteId
    ) throws NoteNotFoundException {
        Resource resource=notesService.sendTextFile(noteId);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
