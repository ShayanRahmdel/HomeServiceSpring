package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    CustomerOrder saveOrder(CustomerOrder customerOrder);

    Optional<CustomerOrder> findById(Integer orderId);

    void deleteById(Integer orderId);

    List<CustomerOrder> seeOrders(Integer expertId);

    void deleteAll();

}
