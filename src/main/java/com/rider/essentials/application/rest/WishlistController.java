package com.rider.essentials.application.rest;

import com.rider.essentials.application.rest.api.IWishlistAPI;
import com.rider.essentials.domain.model.Product;
import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.services.interfaces.IUserService;
import com.rider.essentials.domain.services.interfaces.IWishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class WishlistController implements IWishlistAPI {

    private final IWishlistService wishlistService;
    private final IUserService userService;

    @Autowired
    public WishlistController(IWishlistService wishlistService, IUserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @Override
    public List<Product> getWishlist(String email) {
        User user = userService.getOrCreateByEmail(email);
        return wishlistService.getWishlist(user);
    }

    @Override
    public Map<String, Boolean> addItem(String email, Long productId) {
        User user = userService.getOrCreateByEmail(email);
        boolean added = wishlistService.addItem(user, productId);
        return Map.of("added", added);
    }

    @Override
    public Map<String, Boolean> removeItem(String email, Long productId) {
        User user = userService.getOrCreateByEmail(email);
        boolean removed = wishlistService.removeItem(user, productId);
        return Map.of("removed", removed);
    }

    @Override
    public Map<String, Boolean> exists(String email, Long productId) {
        User user = userService.getOrCreateByEmail(email);
        return Map.of("inWishlist", wishlistService.isInWishlist(user, productId));
    }
}
