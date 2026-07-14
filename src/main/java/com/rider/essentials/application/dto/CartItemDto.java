package com.rider.essentials.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long productId;
    private String name;
    private String image;
    private BigDecimal price;
    private Integer quantity;
    private String size;
    private String brand;
}
