package com.rider.essentials.domain.services;

import com.rider.essentials.domain.model.Product;
import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.model.WishlistItem;
import com.rider.essentials.domain.services.interfaces.IWishlistService;
import com.rider.essentials.repository.IProductRepository;
import com.rider.essentials.repository.IWishlistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rider.essentials.application.constants.Constants.DEFAULT_IMAGE_PRODUCT_ID;

@Slf4j
@Service
public class WishlistService implements IWishlistService {

    private final IWishlistItemRepository wishlistItemRepository;
    private final IProductRepository productRepository;

    @Autowired
    public WishlistService(IWishlistItemRepository wishlistItemRepository, IProductRepository productRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getWishlist(User user) {
        return wishlistItemRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(WishlistItem::getProduct)
                .filter(p -> p.getProductId() != DEFAULT_IMAGE_PRODUCT_ID)
                .toList();
    }

    @Override
    @Transactional
    public boolean addItem(User user, Long productId) {
        if (wishlistItemRepository.existsByUserAndProduct_ProductId(user, productId)) {
            return false;
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        WishlistItem item = new WishlistItem();
        item.setUser(user);
        item.setProduct(product);
        wishlistItemRepository.save(item);
        return true;
    }

    @Override
    @Transactional
    public boolean removeItem(User user, Long productId) {
        return wishlistItemRepository.findByUserAndProduct_ProductId(user, productId)
                .map(item -> {
                    wishlistItemRepository.delete(item);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean isInWishlist(User user, Long productId) {
        return wishlistItemRepository.existsByUserAndProduct_ProductId(user, productId);
    }
}
