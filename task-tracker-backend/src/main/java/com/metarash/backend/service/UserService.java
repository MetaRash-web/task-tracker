package com.metarash.backend.service;

import com.metarash.backend.dto.*;
import com.metarash.backend.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto);
    JwtAuthenticationDto register(UserRegistrationDto userRegistrationDto);
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto);
    UserDto getUserDtoByEmail(String email);
    UserDto getUserDtoByUsername(String username);
    User findUserByUsername(String username);
    boolean isCurrentUser(Long userId, Authentication authentication);
    void deleteUserWithTasks(Long userId);
    UserDto updateUser(Long id, UserDto dto);
}
