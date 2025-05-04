package com.metarash.backend.controller;

import com.metarash.backend.model.dto.JwtAuthenticationDto;
import com.metarash.backend.model.dto.UserDto;
import com.metarash.backend.model.dto.UserRegistrationDto;
import com.metarash.backend.service.UserService;
import com.metarash.backend.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<JwtAuthenticationDto> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        log.info("Attempting to register user with email: {}", registrationDto.getEmail());
        JwtAuthenticationDto jwtAuthenticationDto = userService.register(registrationDto);
        log.info("User registered successfully with email: {}", registrationDto.getEmail());
        return ResponseEntity.ok(jwtAuthenticationDto);
    }

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        log.info("Fetching current user data for email: {}", email);
        UserDto userDto = userService.getUserDtoByUsername(email);
        log.info("Fetched user data for email: {}", email);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @RequestBody UserDto dto,
                                              Authentication authentication) {
        String currentUserEmail = authentication.getName();
        if (!userService.isCurrentUser(id, authentication)) {
            log.error("Access denied for user with email: {} trying to update user with id: {}", currentUserEmail, id);
            throw new AccessDeniedException("Cannot update another user");
        }

        log.info("Updating user with id: {} by user with email: {}", id, currentUserEmail);
        UserDto updatedUser = userService.updateUser(id, dto, authentication);
        log.info("User with id: {} updated successfully", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,
                                        Authentication authentication) {
        String currentUserEmail = authentication.getName();
        if (!userService.isCurrentUser(id, authentication)) {
            log.error("Access denied for user with email: {} trying to delete user with id: {}", currentUserEmail, id);
            throw new AccessDeniedException("Cannot delete another user");
        }

        log.info("Deleting user with id: {} by user with email: {}", id, currentUserEmail);
        userService.deleteUserWithTasks(id, authentication);
        log.info("User with id: {} and related tasks deleted successfully", id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.successMessage("User and tasks deleted successfully"));
    }
}
