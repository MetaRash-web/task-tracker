package com.metarash.backend.service;

import com.metarash.backend.dto.*;
import com.metarash.backend.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;

import javax.naming.AuthenticationException;

public interface UserService {
    JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthenticationDto register(UserDto userDto) throws AuthenticationException;
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;
    UserDto getUserDtoByEmail(String email) throws EntityNotFoundException;
    UserDto getUserDtoByUsername(String username) throws EntityNotFoundException;
    UserDto getUserDtoById(Long id) throws EntityNotFoundException;
    User findUserByUsername(String username);
    User saveUser(UserDto userDto);
}
