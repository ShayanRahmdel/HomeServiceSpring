package com.shayanr.HomeServiceSpring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpertRequestDto {
    @NotBlank
    @Pattern(regexp = "^[A-Z](?=.{1,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Z](?=.{1,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$")
    private String lastName;

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8}$")
    private String password;


    private LocalDate signUpDate;


    private LocalTime signUpTime;


    private byte[] image;

    public void setImage(byte[] image) {
        if (image.length > 300 * 1024) {
            throw new IllegalArgumentException("Image size exceeds the limit of 300KB.");
        }
        this.image = image;
    }

}
