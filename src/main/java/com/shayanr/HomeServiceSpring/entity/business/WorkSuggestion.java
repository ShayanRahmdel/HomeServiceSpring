package com.shayanr.HomeServiceSpring.entity.business;



import com.shayanr.HomeServiceSpring.entity.users.Expert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WorkSuggestion  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate suggestedDate;

    private LocalTime suggestedBeginTime;

    private Double suggestedPrice;

    private String suggestedWorkTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Expert expert;

    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;

}
