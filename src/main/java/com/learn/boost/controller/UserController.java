package com.learn.boost.controller;

import com.learn.boost.dto.UserResponseDto;
import com.learn.boost.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("/alldto")
    public List<UserResponseDto> getAllDtos(){
//        return new  ResponseEntity<>().ok().build(userService.getAllUsers());
        return userService.getAllUsers();
    }
}
