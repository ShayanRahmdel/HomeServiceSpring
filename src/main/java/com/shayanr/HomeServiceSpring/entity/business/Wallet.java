package com.shayanr.HomeServiceSpring.entity.business;



import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
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
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double amount;

    @OneToOne(mappedBy = "wallet")
    private Customer customer;

    @OneToOne(mappedBy = "wallet")
    private Expert expert;

    public Wallet(Double amount) {
        this.amount = amount;
    }
}
