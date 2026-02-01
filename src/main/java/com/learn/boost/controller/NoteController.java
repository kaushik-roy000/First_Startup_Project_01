package com.learn.boost.controller;

import com.learn.boost.dto.NoteResponseDto;
import com.learn.boost.model.Notes;
import com.learn.boost.service.NotesService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class NoteController {
    private NotesService notesService;

    public NoteController(NotesService notesService) {
        this.notesService = notesService;
    }
    @PostMapping("uploadNote/{userId}")
    public ResponseEntity<String> uploadTextFile(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("name") String noteName,
                                                 @RequestParam("rating") int ratting,
                                                 @PathVariable String userId) throws IOException {
        Notes notes= notesService.createNote(noteName,title,ratting);
        String response=notesService.saveNotesWithPaths(userId,notes,file);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/notes/{userid}")
    public ResponseEntity<List<NoteResponseDto>> getNotes(
            @PathVariable String userid
    ){

        List<NoteResponseDto> response=notesService.getNotesDtoByUserId(userid);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/note/{userId}/{noteId}")
    public ResponseEntity<Resource> getUserNote(
            @PathVariable String userId,
            @PathVariable String noteId
    ){
        Resource resource=notesService.sendTextFile(noteId);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN) // Tells the browser it's a text file
                // Optional: Header below makes the browser download the file instead of opening it
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
