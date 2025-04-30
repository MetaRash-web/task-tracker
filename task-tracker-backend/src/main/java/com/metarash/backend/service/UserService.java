package com.metarash.backend.service;

import com.metarash.backend.model.dto.*;
import com.metarash.backend.model.entity.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {
    JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto);
    JwtAuthenticationDto register(UserRegistrationDto userRegistrationDto);
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto);
    UserDto getUserDtoByEmail(String email);
    UserDto getUserDtoByUsername(String username);
    Optional<User> findUserByUsername(String username);
    boolean isCurrentUser(Long userId, Authentication authentication);
    void deleteUserWithTasks(Long userId, Authentication authentication);
    UserDto updateUser(Long id, UserDto dto, Authentication authentication);
}
