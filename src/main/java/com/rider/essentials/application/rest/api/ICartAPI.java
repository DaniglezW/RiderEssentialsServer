package com.rider.essentials.application.rest.api;

import com.rider.essentials.application.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rider.essentials.application.constants.Constants.API_BASE;
import static com.rider.essentials.application.constants.Constants.USER_EMAIL_HEADER;

@RequestMapping(API_BASE)
public interface ICartAPI {

    @GetMapping("/cart")
    List<CartItemDto> getCart(@RequestHeader(USER_EMAIL_HEADER) String email);

    @GetMapping("/cart/summary")
    CartSummaryDto getSummary(@RequestHeader(USER_EMAIL_HEADER) String email);

    @PostMapping("/cart/items")
    CartItemDto addItem(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @RequestBody AddCartItemRequest request);

    @PutMapping("/cart/items")
    CartItemDto updateItem(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @RequestBody UpdateCartItemRequest request);

    @DeleteMapping("/cart/items")
    void removeItem(
            @RequestHeader(USER_EMAIL_HEADER) String email,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "X") String size);

    @DeleteMapping("/cart")
    void clearCart(@RequestHeader(USER_EMAIL_HEADER) String email);
}
