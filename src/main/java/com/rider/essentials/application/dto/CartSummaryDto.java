package com.rider.essentials.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartSummaryDto {
    private java.math.BigDecimal subtotal;
    private java.math.BigDecimal shipping;
    private java.math.BigDecimal total;
    private Integer itemCount;
}
