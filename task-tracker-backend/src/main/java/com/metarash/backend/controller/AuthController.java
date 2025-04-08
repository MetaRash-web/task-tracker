package com.metarash.backend.controller;

import com.metarash.backend.annotations.RateLimit;
import com.metarash.backend.dto.JwtAuthenticationDto;
import com.metarash.backend.dto.RefreshTokenDto;
import com.metarash.backend.dto.UserCredentialsDto;
import com.metarash.backend.dto.UserDto;
import com.metarash.backend.service.UserService;
import com.metarash.backend.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        JwtAuthenticationDto jwtAuthenticationDto = userService.signIn(userCredentialsDto);
        UserDto userDto = userService.getUserDtoByEmail(userCredentialsDto.getEmail());
        return ResponseEntity.ok(ResponseUtils.buildAuthResponse(jwtAuthenticationDto, userDto));
    }

    @PostMapping("/refresh")
    @RateLimit(value = 10, key = "ip")
    public ResponseEntity<JwtAuthenticationDto> refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        JwtAuthenticationDto jwtAuthenticationDto = userService.refreshToken(refreshTokenDto);
        return ResponseEntity.ok(jwtAuthenticationDto);
    }
}
