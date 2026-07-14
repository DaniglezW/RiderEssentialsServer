package com.rider.essentials.domain.services;

import com.rider.essentials.application.dto.UpdateUserProfileRequest;
import com.rider.essentials.application.dto.UserProfileDto;
import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.services.interfaces.IUserService;
import com.rider.essentials.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User getOrCreateByEmail(String email) {
        String normalized = normalizeEmail(email);
        return userRepository.findByEmailIgnoreCase(normalized)
                .orElseGet(() -> {
                    User user = new User();
                    user.setEmail(normalized);
                    user.setName("Rider");
                    return userRepository.save(user);
                });
    }

    @Override
    public UserProfileDto getProfile(String email) {
        User user = getOrCreateByEmail(email);
        return toDto(user);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(String email, UpdateUserProfileRequest request) {
        User user = getOrCreateByEmail(email);
        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName().trim());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()
                && !request.getEmail().equalsIgnoreCase(user.getEmail())) {
            String newEmail = normalizeEmail(request.getEmail());
            userRepository.findByEmailIgnoreCase(newEmail).ifPresent(existing -> {
                if (!existing.getUserId().equals(user.getUserId())) {
                    throw new IllegalArgumentException("Email already in use");
                }
            });
            user.setEmail(newEmail);
        }
        return toDto(userRepository.save(user));
    }

    private UserProfileDto toDto(User user) {
        return new UserProfileDto(user.getUserId(), user.getName(), user.getEmail());
    }

    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("User email is required");
        }
        return email.trim().toLowerCase();
    }
}
