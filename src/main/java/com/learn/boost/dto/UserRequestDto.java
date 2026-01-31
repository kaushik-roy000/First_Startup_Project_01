package com.learn.boost.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "invalid email format")
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "date of birth is required")
    private String dateOfBirth;
    @NotBlank(message = "message is required")
    private String password_hash;
}
