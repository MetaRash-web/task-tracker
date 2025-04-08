package com.metarash.backend.controller;

import com.metarash.backend.dto.JwtAuthenticationDto;
import com.metarash.backend.dto.UserDto;
import com.metarash.backend.dto.UserRegistrationDto;
import com.metarash.backend.entity.User;
import com.metarash.backend.service.UserService;
import com.metarash.backend.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<JwtAuthenticationDto> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        JwtAuthenticationDto jwtAuthenticationDto = userService.register(registrationDto);
        return ResponseEntity.ok(jwtAuthenticationDto);
    }

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserDto userDto = userService.getUserDtoByUsername(email);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @RequestBody UserDto dto,
                                              Authentication authentication) {
        System.out.println("current id: " + id + "\ncurrent dto: " + dto + "\ncurrent authentication: " + authentication);
        if (!userService.isCurrentUser(id, authentication)) {
            throw new AccessDeniedException("Cannot update another user");
        }
        UserDto updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,
                                           Authentication authentication) {
        if (!userService.isCurrentUser(id, authentication)) {
            throw new AccessDeniedException("Cannot delete another user");
        }

        userService.deleteUserWithTasks(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.successMessage("Task deleted successfully"));
    }
}
