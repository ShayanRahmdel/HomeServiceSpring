package com.shayanr.HomeServiceSpring.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {

    @Pattern(regexp = "\\b\\d{16}\\b")
    private String cardNumber;
    @Pattern(regexp =  "\\b\\d{3,4}\\b")
    private String cvv2;
    @FutureOrPresent
    private LocalDate expireDate;
    @Pattern(regexp = "\\b\\d{5,6}\\b")
    private String password;
}
