package com.rider.essentials.repository;

import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IWishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUserOrderByCreatedAtDesc(User user);

    Optional<WishlistItem> findByUserAndProduct_ProductId(User user, Long productId);

    boolean existsByUserAndProduct_ProductId(User user, Long productId);
}
