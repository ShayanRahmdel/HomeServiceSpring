package com.shayanr.HomeServiceSpring.dto;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private String comment;

    @Max(value = 5,message = "your score greater than 5")
    @Min(value = 1,message = "your score less than 1")
    private Integer score;
}
