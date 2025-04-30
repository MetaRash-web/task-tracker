package com.metarash.backend.service.impl;

import com.metarash.backend.annotations.RateLimit;
import com.metarash.backend.model.dto.*;
import com.metarash.backend.model.entity.User;
import com.metarash.backend.exceptionHandler.UserAlreadyExistsException;
import com.metarash.backend.mapper.UserMapper;
import com.metarash.backend.repository.TaskRepository;
import com.metarash.backend.repository.UserRepository;
import com.metarash.backend.security.jwt.JwtService;
import com.metarash.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private static final String INVALID_CREDENTIALS_MSG = "Invalid email or password";
    private static final String USER_NOT_FOUND_MSG = "User not found";

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @RateLimit(value = 10)
    public JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) {
        log.info("Attempting sign-in for email: {}", maskEmail(userCredentialsDto.getEmail()));

        User user = userRepository.findByEmail(userCredentialsDto.getEmail())
                .orElseThrow(() -> new AuthenticationServiceException(INVALID_CREDENTIALS_MSG));

        if (!passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())) {
            throw new AuthenticationServiceException(INVALID_CREDENTIALS_MSG);
        }

        log.info("User authenticated successfully for email: {}", maskEmail(user.getEmail()));
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    @RateLimit(value = 10)
    public JwtAuthenticationDto register(UserRegistrationDto userRegistrationDto) {
        validateRegistrationData(userRegistrationDto);

        User user = userMapper.fromRegistrationDto(userRegistrationDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        log.info("User registered successfully with ID: {}", savedUser.getId());
        return jwtService.generateAuthToken(savedUser.getEmail());
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        log.info("Attempting to refresh token");

        if (!jwtService.isValidRefreshToken(refreshToken)) {
            log.warn("Invalid refresh token provided");
            throw new AuthenticationServiceException("Invalid refresh token");
        }

        String email = jwtService.getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MSG));

        log.info("Refresh token valid, returning new auth token for user ID: {}", user.getId());
        return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
    }

    @Override
    public UserDto getUserDtoByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MSG));
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MSG));
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUserWithTasks(Long userId, Authentication authentication) {
        if (!isCurrentUser(userId, authentication)) {
            throw new AccessDeniedException("Unauthorized operation");
        }

        log.info("Deleting user with ID: {} and associated tasks", userId);
        taskRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto, Authentication authentication) {
        if (!isCurrentUser(id, authentication)) {
            throw new AccessDeniedException("Unauthorized operation");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MSG));

        validateUpdateData(user, dto);

        log.info("Updating user with ID: {}", id);
        userMapper.updateUserFromDto(user, dto);
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public boolean isCurrentUser(Long userId, Authentication authentication) {
        return userRepository.existsByIdAndUsername(userId, authentication.getName());
    }

    private void validateRegistrationData(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Registration attempt with existing email: {}", maskEmail(dto.getEmail()));
            throw new UserAlreadyExistsException("This email is already taken");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            log.warn("Registration attempt with existing username: {}", dto.getUsername());
            throw new UserAlreadyExistsException("This username is already taken");
        }
    }

    private void validateUpdateData(User existingUser, UserDto dto) {
        if (dto.getEmail() != null && !existingUser.getEmail().equals(dto.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new UserAlreadyExistsException("Email already taken");
            }
        }

        if (dto.getUsername() != null && !existingUser.getUsername().equals(dto.getUsername())) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new UserAlreadyExistsException("Username already taken");
            }
        }
    }

    private String maskEmail(String email) {
        return email.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");
    }
}