package com.shayanr.HomeServiceSpring.dto;

import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponseDto {
    private Double proposedPrice;

    private String jobDescription;

    private LocalDate workDate;

    private LocalTime timeDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Address address;
}
