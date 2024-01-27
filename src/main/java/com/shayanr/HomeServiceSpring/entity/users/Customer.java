package com.shayanr.HomeServiceSpring.entity.users;




import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.business.Comment;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "customer")
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer")
    private List<CustomerOrder> customerOrders;

    @Override
    public String toString() {
        return "Customer id " + getId()+"\n"+
                "Customer name " + getFirstName()+" "+ getLastName()+"\n"+
                "Customer email " + getEmail() +"\n"+
                "Customer password " + getPassword();
    }

    public Customer(String firstName, String lastName, String email, String password, LocalDate signUpDate, LocalTime signUpTime) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        setSignUpDate(signUpDate);
        setSignUpTime(signUpTime);
    }
}
