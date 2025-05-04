package com.metarash.backend.mapper.impl;

import com.metarash.backend.model.dto.UserDto;
import com.metarash.backend.model.dto.UserRegistrationDto;
import com.metarash.backend.model.entity.User;
import com.metarash.backend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .telegram(user.getTelegram())
                .build();

        return userDto;
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

    @Override
    public void updateUserFromDto(User user, UserDto dto) {
        if (dto == null) {
            return;
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getTelegram() != null) {
            user.setTelegram(dto.getTelegram());
        }
        // Пароль обрабатывается отдельно в сервисе
    }
}
