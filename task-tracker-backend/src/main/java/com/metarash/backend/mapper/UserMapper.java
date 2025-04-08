package com.metarash.backend.mapper;

import com.metarash.backend.dto.UserDto;
import com.metarash.backend.dto.UserRegistrationDto;
import com.metarash.backend.entity.User;

public interface UserMapper {
    UserDto toDto(User user);

    User fromRegistrationDto(UserRegistrationDto userRegistrationDto);

    User toEntity(UserDto userDto);
}
