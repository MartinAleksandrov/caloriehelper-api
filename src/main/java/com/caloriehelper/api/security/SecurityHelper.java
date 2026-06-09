package com.caloriehelper.api.security;

import com.caloriehelper.api.entity.User;
import com.caloriehelper.api.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityHelper {

    private final UserService userService;

    public SecurityHelper(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("No authenticated user");
        }
        String username = authentication.getName();
        return userService.findByUsername(username)
                .orElseThrow(() -> new SecurityException("User not found: " + username));
    }
}