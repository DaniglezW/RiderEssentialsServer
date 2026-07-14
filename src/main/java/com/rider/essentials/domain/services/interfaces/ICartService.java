package com.rider.essentials.domain.services.interfaces;

import com.rider.essentials.application.dto.*;
import com.rider.essentials.domain.model.User;

import java.util.List;

public interface ICartService {
    List<CartItemDto> getCart(User user);

    CartSummaryDto getSummary(User user);

    CartItemDto addItem(User user, AddCartItemRequest request);

    CartItemDto updateItem(User user, UpdateCartItemRequest request);

    void removeItem(User user, Long productId, String size);

    void clearCart(User user);
}
