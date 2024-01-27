package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findById(Integer customerId);

    void deleteById(Integer customerId);

    List<Customer> findAll();

    Customer signUp(Customer customer);

    Customer changePassword(Integer customerId,String newPassword,String confirmPassword);

    CustomerOrder createOrder(CustomerOrder customerOrder, Integer category, Integer subDutyId, Integer customerId);

    Address createAddress(Address address, Integer customerId, Integer orderId);

    List<WorkSuggestion> seeSuggestionsByPrice(Integer customerId);
    List<WorkSuggestion> seeSuggestionsByExpertScore(Integer customerId);


    CustomerOrder acceptSuggest(Integer suggestId);

    CustomerOrder updateOrderToBegin(Integer orderId, Integer suggestionId, LocalDate date);

    CustomerOrder updateOrderToEnd(Integer orderId);

    void deleteAll();

}
