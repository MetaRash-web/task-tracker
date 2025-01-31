package com.metarash.backend.controller;

import com.metarash.backend.dto.JwtAuthenticationDto;
import com.metarash.backend.dto.RefreshTokenDto;
import com.metarash.backend.dto.UserCredentialsDto;
import com.metarash.backend.dto.UserDto;
import com.metarash.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:80")
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        JwtAuthenticationDto jwtAuthenticationDto = userService.signIn(userCredentialsDto);
        UserDto userDto = userService.getUserDtoByEmail(userCredentialsDto.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtAuthenticationDto.getToken());  // Access token
        response.put("refreshToken", jwtAuthenticationDto.getRefreshToken());  // Refresh token
        response.put("user", userDto);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        return userService.refreshToken(refreshTokenDto);
    }
}
