package com.metarash.backend.service.impl;

import com.metarash.backend.dto.*;
import com.metarash.backend.entity.User;
import com.metarash.backend.exceptionHandler.UserAlreadyExistsException;
import com.metarash.backend.mapper.UserMapper;
import com.metarash.backend.repository.UserRepository;
import com.metarash.backend.security.jwt.JwtService;
import com.metarash.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) {
        User user = findByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public JwtAuthenticationDto register(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("This email is already taken");
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if(refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationServiceException("Invalid refresh token");
    }

    @Override
    public UserDto getUserDtoByEmail(String email) throws EntityNotFoundException {
        return userMapper.toDto(userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    @Override
    public UserDto getUserDtoByUsername(String username) throws EntityNotFoundException {
        return userMapper.toDto(userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    @Override
    public UserDto getUserDtoById(Long id) throws EntityNotFoundException {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private User findByCredentials(UserCredentialsDto userCredentialsDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialsDto.getEmail());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationServiceException("Invalid email or password");
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
