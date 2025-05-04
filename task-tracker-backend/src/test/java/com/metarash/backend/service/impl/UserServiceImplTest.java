package com.metarash.backend.service.impl;

import com.metarash.backend.model.dto.UserDto;
import com.metarash.backend.model.entity.User;
import com.metarash.backend.mapper.UserMapper;
import com.metarash.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserDtoByEmail_returnsUserDto_whenUserExists() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        UserDto userDto = new UserDto();
        userDto.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getUserDtoByEmail(email);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void getUserDtoByEmail_throwsException_whenUserNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserDtoByEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, never()).toDto(any());
    }
}
