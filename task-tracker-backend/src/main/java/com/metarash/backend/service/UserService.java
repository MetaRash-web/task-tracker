package com.metarash.backend.service;

import com.metarash.backend.dto.*;
import com.metarash.backend.entity.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import javax.naming.AuthenticationException;

public interface UserService {
    JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthenticationDto register(UserDto userDto) throws AuthenticationException;
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;
    UserDto getUserDtoByEmail(String email) throws ChangeSetPersister.NotFoundException;
    UserDto getUserDtoByUsername(String username) throws ChangeSetPersister.NotFoundException;
    UserDto getUserDtoById(Long id) throws ChangeSetPersister.NotFoundException;
    User findUserByUsername(String username);
    User saveUser(UserDto userDto);
}
