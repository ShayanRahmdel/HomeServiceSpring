package com.shayanr.HomeServiceSpring.repositoy;


import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder,Integer> {
    @Query("SELECT o FROM CustomerOrder o " +
            "JOIN o.subDuty sd " +
            "JOIN sd.experts e " +
            "WHERE e.id = :expertId AND o.orderStatus = 'WAITING_EXPERT_SUGESTION' OR o.orderStatus = 'WAITING_EXPERT_SELECTION'")
    List<CustomerOrder> seeOrders(@Param("expertId") Integer expertId);
}