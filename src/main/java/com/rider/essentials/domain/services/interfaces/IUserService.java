package com.rider.essentials.domain.services.interfaces;

import com.rider.essentials.application.dto.UpdateUserProfileRequest;
import com.rider.essentials.application.dto.UserProfileDto;
import com.rider.essentials.domain.model.User;

public interface IUserService {
    User getOrCreateByEmail(String email);

    UserProfileDto getProfile(String email);

    UserProfileDto updateProfile(String email, UpdateUserProfileRequest request);
}
