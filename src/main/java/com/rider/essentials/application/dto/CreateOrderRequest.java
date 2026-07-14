package com.rider.essentials.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private String fullName;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String phone;
    private String paymentMethod;
}
