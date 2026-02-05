package com.learn.boost.mapper;

import com.learn.boost.dto.NoteRequestDto;
import com.learn.boost.dto.NoteResponseDto;
import com.learn.boost.model.Notes;

import java.nio.file.Paths;
import java.util.UUID;

public class NoteMapper {
    public static NoteResponseDto noteToDto(Notes notes){
        NoteResponseDto dto=new NoteResponseDto();
        dto.setName(notes.getNotes_name());
        dto.setTitle(notes.getTitle());
        dto.setId(notes.getNotesId());
        int rating=notes.getRating();
        dto.setRating(notes.getRating()+"");
        return dto;
    }
    public static Notes dtoToNote(NoteRequestDto dto){
        Notes note = new Notes();
        note.setNotes_name(dto.getNoteName());
        note.setTitle(dto.getNoteTitle());
        note.setRating(dto.getRating()%11);
        note.setNotesId(UUID.randomUUID().toString());
        return note;

    }
}
