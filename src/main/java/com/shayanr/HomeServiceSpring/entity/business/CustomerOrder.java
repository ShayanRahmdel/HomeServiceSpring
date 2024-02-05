package com.shayanr.HomeServiceSpring.entity.business;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "order_table")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double proposedPrice;

    private String jobDescription;

    private LocalDate workDate;

    private LocalTime timeDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @ManyToOne()
    @JsonIgnore
    private SubDuty subDuty;

    @OneToOne
    @JsonIgnore
    private Comment comment;


    @OneToMany(mappedBy = "customerOrder")
    @JsonIgnore
    private List<WorkSuggestion> workSuggestions;


    @OneToOne
    private Address address;


    public CustomerOrder(Double proposedPrice, String jobDescription, LocalDate workDate, LocalTime timeDate) {
        this.proposedPrice = proposedPrice;
        this.jobDescription = jobDescription;
        this.workDate = workDate;
        this.timeDate = timeDate;

    }

    @Override
    public String toString() {
        return "Order id  " + getId() + "\n"+
                "proposedPrice " + proposedPrice +"\n"+
                "jobDescription " + jobDescription + "\n" +
                "workDate " + workDate + "\n" +
                "timeDate " + timeDate +"\n"+
                "Address " + address + "\n" +
                "=============================="+"\n";
    }
}
