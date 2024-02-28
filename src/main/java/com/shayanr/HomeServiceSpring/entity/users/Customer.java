package com.shayanr.HomeServiceSpring.entity.users;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.business.Comment;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Comment> comments;



    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<CustomerOrder> customerOrders;

    @Override
    public String toString() {
        return "Customer id " + getId() + "\n" +
                "Customer name " + getFirstName() + " " + getLastName() + "\n" +
                "Customer email " + getEmail() + "\n" +
                "Customer password " + getPassword();
    }

}
