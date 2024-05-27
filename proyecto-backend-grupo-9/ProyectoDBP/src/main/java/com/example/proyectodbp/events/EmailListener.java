package com.example.proyectodbp.events;

import com.example.proyectodbp.email.domain.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {
    private final EmailService emailService;

    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void handleHelloEmailEvent(HelloEmailEvent event) {
        emailService.sendSimpleMessage(event.getEmail(), "Ride Created", "Your ride has been created!");
    }
}
