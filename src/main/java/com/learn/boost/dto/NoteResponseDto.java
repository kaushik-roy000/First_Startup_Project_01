package com.learn.boost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseDto {
    private String name;
    private String title;
    private String rating;
    private String id;
    private Path path;
}
