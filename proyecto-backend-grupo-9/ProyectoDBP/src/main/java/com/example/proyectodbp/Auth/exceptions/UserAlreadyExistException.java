package com.example.proyectodbp.Auth.exceptions;




public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
