package com.metarash.backend.controller;

import com.metarash.backend.dto.JwtAuthenticationDto;
import com.metarash.backend.dto.UserDto;
import com.metarash.backend.exceptionHandler.UserAlreadyExistsException;
import com.metarash.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:80")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.register(userDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Registration failed " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Unauthorized"));
        }

        String email = authentication.getName();
        try {
            UserDto userDto = userService.getUserDtoByUsername(email);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Failed to retrieve user information"));
        }
    }
}
