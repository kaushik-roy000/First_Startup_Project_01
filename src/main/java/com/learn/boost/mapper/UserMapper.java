package com.learn.boost.mapper;

import com.learn.boost.dto.UserResponseDto;
import com.learn.boost.model.User;

public class UserMapper {
    public static UserResponseDto toDTO(User user){
        UserResponseDto userResponseDto=new UserResponseDto();
        userResponseDto.setId(user.getUserId().toString());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }
}
