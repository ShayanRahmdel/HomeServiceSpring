package com.shayanr.HomeServiceSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubDutyDto {

    private String title;

    private String description;

    private Double basePrice;
}
