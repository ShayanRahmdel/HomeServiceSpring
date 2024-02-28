package com.shayanr.HomeServiceSpring.entity.business;



import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private LocalTime workduration;

    @ManyToOne
    @JsonIgnore
    private Expert expert;

    @ManyToOne
    @JsonIgnore
    private CustomerOrder customerOrder;


    @Override
    public String toString() {
        return "WorkSuggestion" +"\n" +
                "id " + id + "\n" +
                "suggestedDate  " + suggestedDate +"\n" +
                "suggestedBeginTime  " + suggestedBeginTime +"\n" +
                "suggestedPrice " + suggestedPrice +"\n" +
                "suggestedWorkTime " + workduration + "\n" +
                "expert " + expert +"\n";
    }
}
