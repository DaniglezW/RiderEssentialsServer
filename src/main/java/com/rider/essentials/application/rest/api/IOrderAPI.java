package com.rider.essentials.application.rest.api;

import com.rider.essentials.application.dto.CreateOrderRequest;
import com.rider.essentials.application.dto.OrderDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rider.essentials.application.constants.Constants.API_BASE;
import static com.rider.essentials.application.constants.Constants.USER_EMAIL_HEADER;

@RequestMapping(API_BASE)
public interface IOrderAPI {

    @GetMapping("/orders")
    List<OrderDto> getOrders(@RequestHeader(USER_EMAIL_HEADER) String email);

    @PostMapping("/orders")
    OrderDto createOrder(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @RequestBody CreateOrderRequest request);
}
