package com.metarash.backend.controller;

import com.metarash.backend.dto.JwtAuthenticationDto;
import com.metarash.backend.dto.UserDto;
import com.metarash.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:80")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        JwtAuthenticationDto jwtAuthenticationDto = userService.register(userDto);
        return ResponseEntity.ok(jwtAuthenticationDto);
    }

    @GetMapping
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserDto userDto = userService.getUserDtoByUsername(email);
        return ResponseEntity.ok(userDto);
    }
}
