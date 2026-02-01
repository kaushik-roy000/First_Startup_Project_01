package com.learn.boost.mapper;

import com.learn.boost.dto.NoteResponseDto;
import com.learn.boost.model.Notes;

import java.nio.file.Paths;

public class NoteMapper {
    public static NoteResponseDto noteToDto(Notes notes){
        NoteResponseDto dto=new NoteResponseDto();
        dto.setName(notes.getNotes_name());
        dto.setTitle(notes.getTitle());
        dto.setId(notes.getNotesId());
        dto.setPath(Paths.get( notes.getNotes_path()));
        int rating=notes.getRating();
        dto.setRating(notes.getRating()+"");
        return dto;
    }
}
