package com.rider.essentials.application.rest;

import com.rider.essentials.application.dto.CreateUserBikeRequest;
import com.rider.essentials.application.dto.UserBikeDto;
import com.rider.essentials.application.rest.api.IGarageAPI;
import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.services.interfaces.IGarageService;
import com.rider.essentials.domain.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class GarageController implements IGarageAPI {

    private final IGarageService garageService;
    private final IUserService userService;

    @Autowired
    public GarageController(IGarageService garageService, IUserService userService) {
        this.garageService = garageService;
        this.userService = userService;
    }

    @Override
    public List<UserBikeDto> getBikes(String email) {
        User user = userService.getOrCreateByEmail(email);
        return garageService.getBikes(user);
    }

    @Override
    public UserBikeDto addBike(String email, CreateUserBikeRequest request) {
        User user = userService.getOrCreateByEmail(email);
        return garageService.addBike(user, request);
    }

    @Override
    public void removeBike(String email, Long bikeId) {
        User user = userService.getOrCreateByEmail(email);
        garageService.removeBike(user, bikeId);
    }

    @Override
    public UserBikeDto setPrimary(String email, Long bikeId) {
        User user = userService.getOrCreateByEmail(email);
        return garageService.setPrimary(user, bikeId);
    }
}
