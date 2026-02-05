package com.learn.boost.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NoteRequestDto {
    @NotBlank(message = "name is required")
    private String noteName;
    @NotBlank(message = "title is required")
    private String noteTitle;
    @NotBlank(message = "rating is required")
    private int rating;
//    @NotBlank(message = "file is required")
//    private MultipartFile file;
}
