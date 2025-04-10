package com.metarash.backend.mapper.impl;

import com.metarash.backend.dto.UserDto;
import com.metarash.backend.dto.UserRegistrationDto;
import com.metarash.backend.entity.User;
import com.metarash.backend.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .telegram(user.getTelegram())
                .password(null)
                .build();
    }

    @Override
    public User fromRegistrationDto(UserRegistrationDto userRegistrationDto) {
        if(userRegistrationDto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setTelegram(userRegistrationDto.getTelegram());
        user.setPassword(userRegistrationDto.getPassword());
        user.setRoles("USER");
        user.setEnabled(true);

        return user;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        user.setRoles("USER");
        user.setEnabled(true);

        return user;
    }
}
