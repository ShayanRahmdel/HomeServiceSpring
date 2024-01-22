package com.shayanr.HomeServiceSpring.repositoy;


import com.shayanr.HomeServiceSpring.entity.business.Order;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query("SELECT o FROM Order o " +
            "JOIN o.subDuty sd " +
            "JOIN sd.experts e " +
            "WHERE e.id = :expertId AND o.orderStatus = 'WAITING_EXPERT_SUGESTION' OR o.orderStatus = 'WAITING_EXPERT_SELECTION'")
    List<Order> seeOrders(@Param("expertId") Integer expertId);
}