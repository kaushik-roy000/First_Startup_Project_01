package com.learn.boost.service;

import com.learn.boost.dto.UserResponseDto;
import com.learn.boost.mapper.UserMapper;
import com.learn.boost.model.User;
import com.learn.boost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public List<UserResponseDto> getAllUsers(){
//        List<User> users = userRepository.findAll();
//        List<UserResponseDto> userResponseDtoList=new ArrayList<>();
//        for(User u:users){
//            userResponseDtoList.add(UserMapper.toDTO(u));
//        }
//
//        return  userResponseDtoList;
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}
