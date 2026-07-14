package com.rider.essentials.application.rest;

import com.rider.essentials.application.rest.api.IUserAPI;
import com.rider.essentials.application.dto.UpdateUserProfileRequest;
import com.rider.essentials.application.dto.UserProfileDto;
import com.rider.essentials.domain.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController implements IUserAPI {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserProfileDto getProfile(String email) {
        log.info("Get profile for {}", email);
        return userService.getProfile(email);
    }

    @Override
    public UserProfileDto updateProfile(String email, UpdateUserProfileRequest request) {
        log.info("Update profile for {}", email);
        return userService.updateProfile(email, request);
    }
}
