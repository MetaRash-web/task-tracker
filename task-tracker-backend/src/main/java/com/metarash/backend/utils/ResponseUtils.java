package com.metarash.backend.utils;

import com.metarash.backend.model.dto.JwtAuthenticationDto;
import com.metarash.backend.model.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static Map<String, Object> buildAuthResponse(JwtAuthenticationDto jwtAuth, UserDto userDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtAuth.getToken());
        response.put("refreshToken", jwtAuth.getRefreshToken());
        response.put("user", userDto);
        return response;
    }

    public static ResponseEntity<?> successMessage(String message) {
        return ResponseEntity.ok(Collections.singletonMap("message", message));
    }
}
