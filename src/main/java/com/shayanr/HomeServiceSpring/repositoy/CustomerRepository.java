package com.shayanr.HomeServiceSpring.repositoy;


import com.example.homeservicespringdata.entity.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {


}
