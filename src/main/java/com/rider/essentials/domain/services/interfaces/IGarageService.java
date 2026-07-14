package com.rider.essentials.domain.services.interfaces;

import com.rider.essentials.application.dto.CreateUserBikeRequest;
import com.rider.essentials.application.dto.UserBikeDto;
import com.rider.essentials.domain.model.User;

import java.util.List;

public interface IGarageService {
    List<UserBikeDto> getBikes(User user);

    UserBikeDto addBike(User user, CreateUserBikeRequest request);

    void removeBike(User user, Long bikeId);

    UserBikeDto setPrimary(User user, Long bikeId);
}
