package com.learn.boost.service;

import com.learn.boost.dto.UserRequestDto;
import com.learn.boost.dto.UserResponseDto;
import com.learn.boost.mapper.UserMapper;
import com.learn.boost.model.User;
import com.learn.boost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public boolean createUser(UserRequestDto userRequestDto){
        User user=UserMapper.dtoToUser(userRequestDto);
        if(user!=null){
            //user.setUserId(UUID.randomUUID().toString());
            user.setCreated_at(Instant.now().toString());
            user.setRole("user");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthDate = LocalDate.parse(user.getDateOfBirth(), formatter);
            user.setAge(Period.between(birthDate, LocalDate.now()).getYears());
            //user.setUpdated_at(null);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }


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
