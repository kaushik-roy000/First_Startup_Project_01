package com.learn.boost.service;

import com.learn.boost.dto.UserRequestDto;
import com.learn.boost.dto.UserResponseDto;
import com.learn.boost.enums.UserUpdateField;
import com.learn.boost.mapper.UserMapper;
import com.learn.boost.model.User;
import com.learn.boost.model.UserUpdateHistory;
import com.learn.boost.repository.UserRepository;
import com.learn.boost.repository.UserUPdateHistory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    public UserService() {
    }

    private  UserRepository userRepository;

    private  UserUPdateHistory userUPdateHistoryRepository;

    public UserService(UserUPdateHistory userUPdateHistoryRepository){
        this.userUPdateHistoryRepository=userUPdateHistoryRepository;
    }

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public UserService(UserUPdateHistory userUPdateHistoryRepository, UserRepository userRepository) {
        this.userUPdateHistoryRepository = userUPdateHistoryRepository;
        this.userRepository = userRepository;
    }

    public boolean createUser(UserRequestDto userRequestDto){
        User user=UserMapper.dtoToUser(userRequestDto);
        if(user!=null){
            //user.setUserId(UUID.randomUUID().toString());
            //user.setCreated_at(Instant.now().toString());
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
    private UserUpdateHistory createHistory(
            User user,
            UserUpdateField field,
            String oldvalue
    ){
        UserUpdateHistory history=new UserUpdateHistory();
        history.setUser(user);
        history.setUpdated_at(LocalDateTime.now());
        history.setUpdatedField(field);
        history.setOld_value(oldvalue);
        return history;
    }
    //updateUser
    public User updatedUser( String userId,UserRequestDto dto){
        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("user not found"));
        //List<UserUpdateHistory> historyList=user.getUpdateHistory();
        if(dto.getName()!=null && !user.getName().equals(dto.getName())){
            String name=user.getName();
            user.setName(dto.getName());
            userUPdateHistoryRepository.save(createHistory(user,UserUpdateField.NAME,name) );
            //historyList.add(createHistory(user,UserUpdateField.NAME,name));
        }
        if(dto.getEmail()!=null && !user.getEmail().equals(dto.getEmail())){
            String email=user.getEmail();
            user.setEmail(dto.getEmail());
            userUPdateHistoryRepository.save(createHistory(user,UserUpdateField.EMAIL,email));
            //historyList.add(createHistory(user,UserUpdateField.EMAIL,email));
        }
        if(dto.getPassword_hash() !=null && !user.getPassword_hash().equals(dto.getPassword_hash())){
            String password=user.getPassword_hash();
            user.setPassword_hash(dto.getPassword_hash());
            userUPdateHistoryRepository.save(createHistory(user,UserUpdateField.PASSWORD,password));
            //historyList.add(createHistory(user,UserUpdateField.PASSWORD,password));
        }
        return userRepository.save(user);
    }
}
