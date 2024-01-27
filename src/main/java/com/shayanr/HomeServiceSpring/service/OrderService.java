package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;

import java.util.List;

public interface OrderService {

    CustomerOrder saveOrder(CustomerOrder customerOrder);

    CustomerOrder findById(Integer orderId);

    void deleteById(Integer orderId);

    List<CustomerOrder> seeOrders(Integer expertId);

    void deleteAll();

}
