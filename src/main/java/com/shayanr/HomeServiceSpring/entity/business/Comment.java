package com.shayanr.HomeServiceSpring.entity.business;



import com.shayanr.HomeServiceSpring.entity.users.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String comment;


    private Integer score=0;

    @OneToOne(mappedBy = "comment")
    private CustomerOrder customerOrder;

    @ManyToOne()
    private Customer customer;

}
