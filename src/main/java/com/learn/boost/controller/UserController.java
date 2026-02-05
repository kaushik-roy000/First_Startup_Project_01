package com.learn.boost.controller;

import com.learn.boost.dto.UserRequestDto;
import com.learn.boost.dto.UserResponseDto;
import com.learn.boost.model.User;
import com.learn.boost.model.UserUpdateHistory;
import com.learn.boost.service.userServices.UserService;
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
    public ResponseEntity<?> createUsers(@Valid @RequestBody UserRequestDto userRequestDto){
        if( userService.createUser(userRequestDto)){
            return ResponseEntity.ok("user created successfull");
        }else {
            return ResponseEntity.badRequest().body("notcreated user something wrong");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @RequestBody UserRequestDto dto
    ){
        User user= userService.updatedUser(id,dto);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/getallhistory")
    public ResponseEntity<List<UserUpdateHistory>> getAllHistory(){
        List<UserUpdateHistory> userAllHistory=userService.getAllHistory();
        return ResponseEntity.ok(userAllHistory);
    }
    @GetMapping("/getHistory/{id}")
    public ResponseEntity<List<UserUpdateHistory>> getHistoryById(@PathVariable String id){
        List<UserUpdateHistory> oneUserHistory=userService.getHistoryById(id);
        return ResponseEntity.ok(oneUserHistory);
    }
}
