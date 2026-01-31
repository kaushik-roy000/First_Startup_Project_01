package com.learn.boost.mapper;

import com.learn.boost.dto.UserRequestDto;
import com.learn.boost.dto.UserResponseDto;
import com.learn.boost.model.User;

public class UserMapper {
    public static UserResponseDto toDTO(User user){
        UserResponseDto userResponseDto=new UserResponseDto();
        userResponseDto.setId(user.getUserId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }
    public static User dtoToUser(UserRequestDto userRequestDto){
        User user=new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setDateOfBirth(userRequestDto.getDateOfBirth());
        user.setPassword_hash(userRequestDto.getPassword_hash());
        return user;
    }
}
