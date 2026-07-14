package com.rider.essentials.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBikeDto {
    private Long bikeId;
    private String make;
    private String model;
    private Integer year;
    private String nickname;
    private Boolean isPrimary;
}
