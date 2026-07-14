package com.rider.essentials.domain.services;

import com.rider.essentials.application.dto.*;
import com.rider.essentials.application.utils.ProductImageUtils;
import com.rider.essentials.domain.model.CartItem;
import com.rider.essentials.domain.model.Product;
import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.services.interfaces.ICartService;
import com.rider.essentials.repository.ICartItemRepository;
import com.rider.essentials.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.rider.essentials.application.constants.Constants.DEFAULT_IMAGE_PRODUCT_ID;

@Slf4j
@Service
public class CartService implements ICartService {

    private static final BigDecimal FREE_SHIPPING_THRESHOLD = new BigDecimal("75.00");
    private static final BigDecimal SHIPPING_COST = new BigDecimal("6.95");

    private final ICartItemRepository cartItemRepository;
    private final IProductRepository productRepository;

    @Autowired
    public CartService(ICartItemRepository cartItemRepository, IProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartItemDto> getCart(User user) {
        return cartItemRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CartSummaryDto getSummary(User user) {
        List<CartItem> items = cartItemRepository.findByUserOrderByCreatedAtDesc(user);
        BigDecimal subtotal = items.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal shipping = calculateShipping(subtotal);
        int count = items.stream().mapToInt(CartItem::getQuantity).sum();
        return new CartSummaryDto(subtotal, shipping, subtotal.add(shipping), count);
    }

    @Override
    @Transactional
    public CartItemDto addItem(User user, AddCartItemRequest request) {
        Product product = getValidProduct(request.getProductId());
        if (product.getStockQuantity() != null && product.getStockQuantity() <= 0) {
            throw new IllegalStateException("Product out of stock");
        }
        String size = normalizeSize(request.getSize());
        int qty = request.getQuantity() != null && request.getQuantity() > 0 ? request.getQuantity() : 1;

        CartItem item = cartItemRepository.findByUserAndProduct_ProductIdAndSize(user, product.getProductId(), size)
                .orElse(null);
        if (item != null) {
            item.setQuantity(item.getQuantity() + qty);
        } else {
            item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(qty);
            item.setSize(size);
        }
        return toDto(cartItemRepository.save(item));
    }

    @Override
    @Transactional
    public CartItemDto updateItem(User user, UpdateCartItemRequest request) {
        String size = normalizeSize(request.getSize());
        CartItem item = cartItemRepository.findByUserAndProduct_ProductIdAndSize(user, request.getProductId(), size)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            cartItemRepository.delete(item);
            return null;
        }
        item.setQuantity(request.getQuantity());
        return toDto(cartItemRepository.save(item));
    }

    @Override
    @Transactional
    public void removeItem(User user, Long productId, String size) {
        cartItemRepository.findByUserAndProduct_ProductIdAndSize(user, productId, normalizeSize(size))
                .ifPresent(cartItemRepository::delete);
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        cartItemRepository.deleteByUser(user);
    }

    private Product getValidProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (product.getProductId() == DEFAULT_IMAGE_PRODUCT_ID) {
            throw new IllegalArgumentException("Invalid product");
        }
        return product;
    }

    private CartItemDto toDto(CartItem item) {
        Product p = item.getProduct();
        return new CartItemDto(
                p.getProductId(),
                p.getName(),
                ProductImageUtils.toBase64DataUrl(p),
                p.getPrice(),
                item.getQuantity(),
                item.getSize(),
                p.getBrand()
        );
    }

    private BigDecimal calculateShipping(BigDecimal subtotal) {
        if (subtotal.compareTo(BigDecimal.ZERO) == 0 || subtotal.compareTo(FREE_SHIPPING_THRESHOLD) >= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return SHIPPING_COST;
    }

    private String normalizeSize(String size) {
        return size == null || size.isBlank() ? "X" : size.trim();
    }
}
