package com.rider.essentials.application.rest.api;

import com.rider.essentials.application.dto.UpdateUserProfileRequest;
import com.rider.essentials.application.dto.UserProfileDto;
import org.springframework.web.bind.annotation.*;

import static com.rider.essentials.application.constants.Constants.API_BASE;
import static com.rider.essentials.application.constants.Constants.USER_EMAIL_HEADER;

@RequestMapping(API_BASE)
public interface IUserAPI {

    @GetMapping("/users/profile")
    UserProfileDto getProfile(@RequestHeader(USER_EMAIL_HEADER) String email);

    @PutMapping("/users/profile")
    UserProfileDto updateProfile(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @RequestBody UpdateUserProfileRequest request);
}
