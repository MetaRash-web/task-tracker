package com.metarash.backend.controller;

import com.metarash.backend.annotations.RateLimit;
import com.metarash.backend.model.dto.JwtAuthenticationDto;
import com.metarash.backend.model.dto.RefreshTokenDto;
import com.metarash.backend.model.dto.UserCredentialsDto;
import com.metarash.backend.model.dto.UserDto;
import com.metarash.backend.service.UserService;
import com.metarash.backend.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    @RateLimit(value = 5)
    public ResponseEntity<?> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        log.info("Attempting to sign in with email: {}", userCredentialsDto.getEmail());
        JwtAuthenticationDto jwtAuthenticationDto = userService.signIn(userCredentialsDto);
        UserDto userDto = userService.getUserDtoByEmail(userCredentialsDto.getEmail());
        log.info("User signed in successfully with email: {}", userCredentialsDto.getEmail());
        return ResponseEntity.ok(ResponseUtils.buildAuthResponse(jwtAuthenticationDto, userDto));
    }

    @PostMapping("/refresh")
    @RateLimit(value = 10, key = "ip")
    public ResponseEntity<JwtAuthenticationDto> refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        log.info("Attempting to refresh token for user with refresh token: {}", refreshTokenDto.getRefreshToken());
        JwtAuthenticationDto jwtAuthenticationDto = userService.refreshToken(refreshTokenDto);
        log.info("Token refreshed successfully for user with refresh token: {}", refreshTokenDto.getRefreshToken());
        return ResponseEntity.ok(jwtAuthenticationDto);
    }
}

