package com.example.proyectodbp.auth.utils;

import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.domain.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {
    private final UserService userService;

    public AuthorizationUtils(UserService userService) {
        this.userService = userService;
    }

    public boolean isAdminOrResourceOwner(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().toArray()[0].toString();
        User passenger = userService.findByEmail(username, role);

        return passenger.getId().equals(id) || passenger.getRole().equals(Role.DRIVER);
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        catch (ClassCastException e) {
            return null;
        }
    }
}