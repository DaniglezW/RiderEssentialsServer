package com.rider.essentials.application.rest.api;

import com.rider.essentials.application.dto.CreateUserBikeRequest;
import com.rider.essentials.application.dto.UserBikeDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rider.essentials.application.constants.Constants.API_BASE;
import static com.rider.essentials.application.constants.Constants.USER_EMAIL_HEADER;

@RequestMapping(API_BASE)
public interface IGarageAPI {

    @GetMapping("/garage/bikes")
    List<UserBikeDto> getBikes(@RequestHeader(USER_EMAIL_HEADER) String email);

    @PostMapping("/garage/bikes")
    UserBikeDto addBike(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @RequestBody CreateUserBikeRequest request);

    @DeleteMapping("/garage/bikes/{bikeId}")
    void removeBike(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @PathVariable Long bikeId);

    @PutMapping("/garage/bikes/{bikeId}/primary")
    UserBikeDto setPrimary(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @PathVariable Long bikeId);
}
