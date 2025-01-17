package com.metarash.backend.mapper;

import com.metarash.backend.dto.UserDto;
import com.metarash.backend.entity.User;

public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
