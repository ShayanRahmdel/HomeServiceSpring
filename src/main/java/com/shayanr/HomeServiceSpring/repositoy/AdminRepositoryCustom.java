package com.shayanr.HomeServiceSpring.repositoy;

import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;

import com.shayanr.HomeServiceSpring.entity.users.User;


import java.time.LocalDate;
import java.util.List;

public interface AdminRepositoryCustom {
    List<User> searchAdminByUser(String name, String lastName, String email, String expertise, Double minRate, Double maxRate);
    List<WorkSuggestion> searchWorkSuggestionByName(String firstName, String lastName);

    List<CustomerOrder> searchOrders(LocalDate startDate, LocalDate endDate, OrderStatus orderStatus,
                                     String category,String subDuty);
}
