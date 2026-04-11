package com.practice.prod_features.exceptions;

import org.aspectj.bridge.IMessage;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String message){
        super(message);
    }
}
