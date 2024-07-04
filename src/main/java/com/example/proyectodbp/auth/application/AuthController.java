package com.example.proyectodbp.auth.application;

import com.example.proyectodbp.auth.domain.AuthService;
import com.example.proyectodbp.auth.dto.JwtAuthResponse;
import com.example.proyectodbp.auth.dto.LoginRequest;
import com.example.proyectodbp.auth.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JdbcTemplate jdbcTemplate;

    public AuthController(AuthService authService, JdbcTemplate jdbcTemplate) {
        this.authService = authService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public  ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/test/connection")
    public ResponseEntity<String> testConnection(){
        try{
            jdbcTemplate.execute("SELECT 1");
            return ResponseEntity.ok("Connection to PostgreSQL database is successful");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error connecting to PostgreSQL database: " + e.getMessage());
        }
    }
}