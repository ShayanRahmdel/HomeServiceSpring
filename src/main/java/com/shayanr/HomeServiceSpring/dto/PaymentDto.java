package com.shayanr.HomeServiceSpring.dto;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
    private String cardNumber;
    private String cvv2;
    @FutureOrPresent
    private String expireDate;
    private String password;
}
