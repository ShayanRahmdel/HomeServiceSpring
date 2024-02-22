package com.shayanr.HomeServiceSpring.repositoy;

import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.users.Customer;

import java.util.List;

public interface CustomerRepositoryCustom {


    List<CustomerOrder> seeOrderByStatus(Integer customerId, String orderStatus);
}
