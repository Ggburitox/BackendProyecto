package com.example.proyectodbp.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class HelloEmailEvent extends ApplicationEvent {
    private final String email;
    private final String message;

    public HelloEmailEvent(String email, String message) {
        super(email);
        this.email = email;
        this.message = message;
    }
}
