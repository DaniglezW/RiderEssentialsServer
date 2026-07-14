package com.rider.essentials.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String id;
    private BigDecimal subtotal;
    private BigDecimal shipping;
    private BigDecimal total;
    private String status;
    private String paymentMethod;
    private String createdAt;
    private ShippingAddressDto address;
    private List<OrderItemDto> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShippingAddressDto {
        private String fullName;
        private String street;
        private String city;
        private String postalCode;
        private String country;
        private String phone;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemDto {
        private Long productId;
        private String name;
        private String image;
        private BigDecimal price;
        private Integer quantity;
        private String size;
        private String brand;
    }
}
