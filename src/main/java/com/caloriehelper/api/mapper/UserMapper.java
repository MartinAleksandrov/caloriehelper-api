package com.caloriehelper.api.mapper;

import com.caloriehelper.api.dto.users.UserResponse;
import com.caloriehelper.api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
}