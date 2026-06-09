package com.caloriehelper.api.controller;

import com.caloriehelper.api.dto.users.UserResponse;
import com.caloriehelper.api.entity.User;
import com.caloriehelper.api.mapper.UserMapper;
import com.caloriehelper.api.security.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final SecurityHelper securityHelper;
    private final UserMapper userMapper;

    public UserController(SecurityHelper securityHelper, UserMapper userMapper) {
        this.securityHelper = securityHelper;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        User user = securityHelper.getCurrentUser(authentication);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }
}