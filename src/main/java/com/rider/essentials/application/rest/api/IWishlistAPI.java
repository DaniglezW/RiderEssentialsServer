package com.rider.essentials.application.rest.api;

import com.rider.essentials.domain.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.rider.essentials.application.constants.Constants.API_BASE;
import static com.rider.essentials.application.constants.Constants.USER_EMAIL_HEADER;

@RequestMapping(API_BASE)
public interface IWishlistAPI {

    @GetMapping("/wishlist")
    List<Product> getWishlist(@RequestHeader(USER_EMAIL_HEADER) String email);

    @PostMapping("/wishlist/{productId}")
    Map<String, Boolean> addItem(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @PathVariable Long productId);

    @DeleteMapping("/wishlist/{productId}")
    Map<String, Boolean> removeItem(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @PathVariable Long productId);

    @GetMapping("/wishlist/{productId}/exists")
    Map<String, Boolean> exists(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @PathVariable Long productId);
}
