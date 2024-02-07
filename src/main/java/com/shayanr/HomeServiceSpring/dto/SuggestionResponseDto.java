package com.shayanr.HomeServiceSpring.dto;

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
public class SuggestionResponseDto {
    private LocalDate suggestedDate;

    private LocalTime suggestedBeginTime;

    private Double suggestedPrice;

    private LocalTime workduration;
}
