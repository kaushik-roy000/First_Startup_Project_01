package com.learn.boost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public  class UserResponseDto {
    private String id;
    private String name;
    private String email;
}

