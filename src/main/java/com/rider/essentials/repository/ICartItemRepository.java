package com.rider.essentials.repository;

import com.rider.essentials.domain.model.CartItem;
import com.rider.essentials.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserOrderByCreatedAtDesc(User user);

    Optional<CartItem> findByUserAndProduct_ProductIdAndSize(User user, Long productId, String size);

    void deleteByUser(User user);
}
