package com.rider.essentials.domain.services.interfaces;

import com.rider.essentials.application.dto.CreateOrderRequest;
import com.rider.essentials.application.dto.OrderDto;
import com.rider.essentials.domain.model.User;

import java.util.List;

public interface IOrderService {
    List<OrderDto> getOrders(User user);

    OrderDto createOrder(User user, CreateOrderRequest request);
}
