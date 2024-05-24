package com.example.proyectodbp.exceptions;

public class EntityAlreadyExists extends RuntimeException{
    public EntityAlreadyExists(String message) {
        super(message);
    }
}
