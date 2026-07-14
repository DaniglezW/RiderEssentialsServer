package com.rider.essentials.application.rest;

import com.rider.essentials.application.dto.CreateOrderRequest;
import com.rider.essentials.application.dto.OrderDto;
import com.rider.essentials.application.rest.api.IOrderAPI;
import com.rider.essentials.domain.model.User;
import com.rider.essentials.domain.services.interfaces.IOrderService;
import com.rider.essentials.domain.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class OrderController implements IOrderAPI {

    private final IOrderService orderService;
    private final IUserService userService;

    @Autowired
    public OrderController(IOrderService orderService, IUserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public List<OrderDto> getOrders(String email) {
        User user = userService.getOrCreateByEmail(email);
        return orderService.getOrders(user);
    }

    @Override
    public OrderDto createOrder(String email, CreateOrderRequest request) {
        User user = userService.getOrCreateByEmail(email);
        return orderService.createOrder(user, request);
    }
}
