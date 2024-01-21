package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.business.Order;
import com.shayanr.HomeServiceSpring.entity.users.Customer;

public interface CustomerService {

    Customer signUp(Customer customer);

    void changePassword(String email, String oldPassword,String newPassword);

    Order createOrder(Order order, Integer category, Integer subDutyId, Integer customerId);

    Address createAddress(Address address, Integer customerId, Integer orderId);

}
