package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.business.Order;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findById(Integer customerId);

    void deleteById(Integer customerId);

    List<Customer> findAll();

    Customer signUp(Customer customer);

    void changePassword(Integer customerId,String newPassword,String confirmPassword);

    Order createOrder(Order order, Integer category, Integer subDutyId, Integer customerId);

    Address createAddress(Address address, Integer customerId, Integer orderId);

    List<WorkSuggestion> seeWorkSuggestions(Integer customerId);

}
