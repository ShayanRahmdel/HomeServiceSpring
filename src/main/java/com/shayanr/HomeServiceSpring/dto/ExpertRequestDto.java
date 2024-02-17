package com.shayanr.HomeServiceSpring.dto;

import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.exception.ValidationException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpertRequestDto {

    @Pattern(regexp = "^[A-Z](?=.{1,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$")
    private String firstName;


    @Pattern(regexp = "^[A-Z](?=.{1,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$")
    private String lastName;

    @Email
    private String email;


    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8}$")
    private String password;

    @FutureOrPresent
    private LocalDate signUpDate;


    private LocalTime signUpTime;

    private MultipartFile image;





}
