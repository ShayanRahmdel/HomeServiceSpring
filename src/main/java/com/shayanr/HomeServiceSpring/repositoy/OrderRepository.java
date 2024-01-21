package com.shayanr.HomeServiceSpring.repositoy;


import com.example.homeservicespringdata.entity.business.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
