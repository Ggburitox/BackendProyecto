package com.example.proyectodbp.events;

import com.example.proyectodbp.email.domain.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {
    @Autowired
    private EmailService emailService;

    @EventListener
    @Async
    public void handleHelloEmailEvent(HelloEmailEvent event) {
        emailService.sendSimpleMessage(event.getEmail(), "Ride Created", "Your ride has been created!");
    }
}
