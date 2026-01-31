package com.learn.boost.controller;

import com.learn.boost.dto.UserRequestDto;
import com.learn.boost.dto.UserResponseDto;
import com.learn.boost.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("/users")
    public ResponseEntity< List<UserResponseDto>> getAllDtos(){
        List<UserResponseDto> dtoUsers=userService.getAllUsers();
        return  ResponseEntity.ok( dtoUsers);
       // return userService.getAllUsers();
    }
    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        if( userService.createUser(userRequestDto)){
            return ResponseEntity.ok("user created successfull");
        }else {
            return ResponseEntity.badRequest().body("notcreated user something wrong");
        }
    }
}
