package com.example.proyectodbp.auth.exceptions;




public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
