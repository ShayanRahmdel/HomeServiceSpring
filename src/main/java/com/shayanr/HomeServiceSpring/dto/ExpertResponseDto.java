package com.shayanr.HomeServiceSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpertResponseDto {

    private String firstName;

    private String lastName;

    private String email;

    private Double overallScore;

}
