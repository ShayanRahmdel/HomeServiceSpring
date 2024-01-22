package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.Order;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderService {

    Order saveOrder(Order order);

    Order findById(Integer orderId);

    void deleteById(Integer orderId);

    List<Order> seeOrders(Integer expertId);

}
