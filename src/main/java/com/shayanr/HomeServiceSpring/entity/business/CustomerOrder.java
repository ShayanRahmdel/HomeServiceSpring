package com.shayanr.HomeServiceSpring.entity.business;



import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import jakarta.persistence.*;
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
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubDuty subDuty;

    @OneToOne
    private Comment comment;


    @OneToMany(mappedBy = "customerOrder")
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
