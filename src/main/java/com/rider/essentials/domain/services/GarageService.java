package com.rider.essentials.domain.services;

import com.rider.essentials.application.dto.CreateUserBikeRequest;
import com.rider.essentials.application.dto.UserBikeDto;
import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.model.UserBike;
import com.rider.essentials.domain.services.interfaces.IGarageService;
import com.rider.essentials.repository.IUserBikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class GarageService implements IGarageService {

    private final IUserBikeRepository userBikeRepository;

    @Autowired
    public GarageService(IUserBikeRepository userBikeRepository) {
        this.userBikeRepository = userBikeRepository;
    }

    @Override
    public List<UserBikeDto> getBikes(User user) {
        return userBikeRepository.findByUserOrderByIsPrimaryDescCreatedAtAsc(user).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UserBikeDto addBike(User user, CreateUserBikeRequest request) {
        if (request.getMake() == null || request.getMake().isBlank()
                || request.getModel() == null || request.getModel().isBlank()) {
            throw new IllegalArgumentException("Make and model are required");
        }
        UserBike bike = new UserBike();
        bike.setUser(user);
        bike.setMake(request.getMake().trim());
        bike.setModel(request.getModel().trim());
        bike.setYear(request.getYear() != null ? request.getYear() : java.time.Year.now().getValue());
        bike.setNickname(request.getNickname());
        bike.setIsPrimary(userBikeRepository.countByUser(user) == 0);
        return toDto(userBikeRepository.save(bike));
    }

    @Override
    @Transactional
    public void removeBike(User user, Long bikeId) {
        UserBike bike = userBikeRepository.findByBikeIdAndUser(bikeId, user)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found"));
        boolean wasPrimary = Boolean.TRUE.equals(bike.getIsPrimary());
        userBikeRepository.delete(bike);
        if (wasPrimary) {
            List<UserBike> remaining = userBikeRepository.findByUserOrderByIsPrimaryDescCreatedAtAsc(user);
            if (!remaining.isEmpty()) {
                UserBike first = remaining.get(0);
                first.setIsPrimary(true);
                userBikeRepository.save(first);
            }
        }
    }

    @Override
    @Transactional
    public UserBikeDto setPrimary(User user, Long bikeId) {
        UserBike target = userBikeRepository.findByBikeIdAndUser(bikeId, user)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found"));
        userBikeRepository.findByUserOrderByIsPrimaryDescCreatedAtAsc(user).forEach(b -> {
            b.setIsPrimary(false);
            userBikeRepository.save(b);
        });
        target.setIsPrimary(true);
        return toDto(userBikeRepository.save(target));
    }

    private UserBikeDto toDto(UserBike bike) {
        return new UserBikeDto(
                bike.getBikeId(),
                bike.getMake(),
                bike.getModel(),
                bike.getYear(),
                bike.getNickname(),
                bike.getIsPrimary()
        );
    }
}
