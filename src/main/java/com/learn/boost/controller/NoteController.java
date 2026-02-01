package com.learn.boost.controller;

import com.learn.boost.model.Notes;
import com.learn.boost.service.NotesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

        return ResponseEntity.ok(notesService.saveNotesWithPaths(userId,notes,file));
    }


}
