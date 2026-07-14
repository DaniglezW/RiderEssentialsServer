package com.rider.essentials.domain.services;

import com.rider.essentials.application.dto.CartSummaryDto;
import com.rider.essentials.application.dto.CreateOrderRequest;
import com.rider.essentials.application.dto.OrderDto;
import com.rider.essentials.application.utils.ProductImageUtils;
import com.rider.essentials.domain.model.*;
import com.rider.essentials.domain.services.interfaces.ICartService;
import com.rider.essentials.domain.services.interfaces.IOrderService;
import com.rider.essentials.repository.ICartItemRepository;
import com.rider.essentials.repository.IOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final ICartItemRepository cartItemRepository;
    private final ICartService cartService;

    @Autowired
    public OrderService(IOrderRepository orderRepository, ICartItemRepository cartItemRepository, ICartService cartService) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
    }

    @Override
    public List<OrderDto> getOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDto createOrder(User user, CreateOrderRequest request) {
        validateRequest(request);
        List<CartItem> cartItems = cartItemRepository.findByUserOrderByCreatedAtDesc(user);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        CartSummaryDto summary = cartService.getSummary(user);

        Order order = new Order();
        order.setOrderCode("RE-" + Long.toString(System.currentTimeMillis(), 36).toUpperCase());
        order.setUser(user);
        order.setSubtotal(summary.getSubtotal());
        order.setShipping(summary.getShipping());
        order.setTotal(summary.getTotal());
        order.setStatus("confirmed");
        order.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "card");
        order.setFullName(request.getFullName().trim());
        order.setStreet(request.getStreet().trim());
        order.setCity(request.getCity().trim());
        order.setPostalCode(request.getPostalCode().trim());
        order.setCountry(request.getCountry() != null ? request.getCountry().trim() : "España");
        order.setPhone(request.getPhone().trim());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Product p = cartItem.getProduct();
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProductId(p.getProductId());
            oi.setProductName(p.getName());
            oi.setPrice(p.getPrice());
            oi.setQuantity(cartItem.getQuantity());
            oi.setSize(cartItem.getSize());
            oi.setBrand(p.getBrand());
            oi.setImageBase64(ProductImageUtils.toBase64DataUrl(p));
            orderItems.add(oi);
        }
        order.setItems(orderItems);

        Order saved = orderRepository.save(order);
        cartService.clearCart(user);
        return toDto(saved);
    }

    private void validateRequest(CreateOrderRequest request) {
        if (request.getFullName() == null || request.getFullName().isBlank()
                || request.getStreet() == null || request.getStreet().isBlank()
                || request.getCity() == null || request.getCity().isBlank()
                || request.getPostalCode() == null || request.getPostalCode().isBlank()
                || request.getPhone() == null || request.getPhone().isBlank()) {
            throw new IllegalArgumentException("Shipping address is incomplete");
        }
    }

    private OrderDto toDto(Order order) {
        OrderDto.ShippingAddressDto address = new OrderDto.ShippingAddressDto(
                order.getFullName(),
                order.getStreet(),
                order.getCity(),
                order.getPostalCode(),
                order.getCountry(),
                order.getPhone()
        );
        List<OrderDto.OrderItemDto> items = order.getItems().stream()
                .map(i -> new OrderDto.OrderItemDto(
                        i.getProductId(),
                        i.getProductName(),
                        i.getImageBase64(),
                        i.getPrice(),
                        i.getQuantity(),
                        i.getSize(),
                        i.getBrand()
                ))
                .toList();
        return new OrderDto(
                order.getOrderCode(),
                order.getSubtotal(),
                order.getShipping(),
                order.getTotal(),
                order.getStatus(),
                order.getPaymentMethod(),
                order.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                address,
                items
        );
    }
}
