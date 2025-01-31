package com.metarash.backend.service;

import com.metarash.backend.dto.*;
import com.metarash.backend.entity.User;

public interface UserService {
    JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto);
    JwtAuthenticationDto register(UserDto userDto);
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto);
    UserDto getUserDtoByEmail(String email);
    UserDto getUserDtoByUsername(String username);
    UserDto getUserDtoById(Long id);
    User findUserByUsername(String username);
    User saveUser(UserDto userDto);
}
