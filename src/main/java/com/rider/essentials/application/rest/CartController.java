package com.rider.essentials.application.rest;

import com.rider.essentials.application.dto.*;
import com.rider.essentials.application.rest.api.ICartAPI;
import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.services.interfaces.ICartService;
import com.rider.essentials.domain.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CartController implements ICartAPI {

    private final ICartService cartService;
    private final IUserService userService;

    @Autowired
    public CartController(ICartService cartService, IUserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @Override
    public List<CartItemDto> getCart(String email) {
        User user = userService.getOrCreateByEmail(email);
        return cartService.getCart(user);
    }

    @Override
    public CartSummaryDto getSummary(String email) {
        User user = userService.getOrCreateByEmail(email);
        return cartService.getSummary(user);
    }

    @Override
    public CartItemDto addItem(String email, AddCartItemRequest request) {
        User user = userService.getOrCreateByEmail(email);
        return cartService.addItem(user, request);
    }

    @Override
    public CartItemDto updateItem(String email, UpdateCartItemRequest request) {
        User user = userService.getOrCreateByEmail(email);
        return cartService.updateItem(user, request);
    }

    @Override
    public void removeItem(String email, Long productId, String size) {
        User user = userService.getOrCreateByEmail(email);
        cartService.removeItem(user, productId, size);
    }

    @Override
    public void clearCart(String email) {
        User user = userService.getOrCreateByEmail(email);
        cartService.clearCart(user);
    }
}
