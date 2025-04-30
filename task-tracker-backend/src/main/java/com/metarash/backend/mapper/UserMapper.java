package com.metarash.backend.mapper;

import com.metarash.backend.model.dto.UserDto;
import com.metarash.backend.model.dto.UserRegistrationDto;
import com.metarash.backend.model.entity.User;

public interface UserMapper {
    UserDto toDto(User user);

    User fromRegistrationDto(UserRegistrationDto userRegistrationDto);

    User toEntity(UserDto userDto);

    void updateUserFromDto(User user, UserDto dto);
}
