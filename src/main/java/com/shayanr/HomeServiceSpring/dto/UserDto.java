package com.shayanr.HomeServiceSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class UserDto  {
    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private LocalDate signUpDate;

    private LocalTime signUpTime;

    private Double overallScore;

    private String subDutyTitle;

}
