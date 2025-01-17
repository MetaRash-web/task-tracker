package com.metarash.backend.mapper.impl;

import com.metarash.backend.dto.UserDto;
import com.metarash.backend.entity.User;
import com.metarash.backend.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    // Преобразование из UserDto в User
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
