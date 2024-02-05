package com.shayanr.HomeServiceSpring.entity.business;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String city;

    private String address;

    private String postalCode;

    @ManyToOne()
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private CustomerOrder customerOrder;

    public Address(String state, String city, String address, String postalCode) {
        this.state = state;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return " ==== Address ====  " +"\n"+
                "state  " + state + "\n" +
                "city   " + city + "\n" +
                "address " + address + "\n" +
                "postalCode  " + postalCode + "\n";
    }
}
