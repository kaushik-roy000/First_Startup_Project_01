package com.learn.boost.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException (String userId){
        super(userId+" :this user is not found");
    }
}
