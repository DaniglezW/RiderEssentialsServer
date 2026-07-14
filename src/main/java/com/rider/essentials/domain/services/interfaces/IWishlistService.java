package com.rider.essentials.domain.services.interfaces;

import com.rider.essentials.domain.model.Product;
import com.rider.essentials.domain.model.User;

import java.util.List;

public interface IWishlistService {
    List<Product> getWishlist(User user);

    boolean addItem(User user, Long productId);

    boolean removeItem(User user, Long productId);

    boolean isInWishlist(User user, Long productId);
}
