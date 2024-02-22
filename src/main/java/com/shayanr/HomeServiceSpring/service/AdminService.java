package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.entity.users.User;

import java.time.LocalDate;
import java.util.List;

public interface AdminService   {

    DutyCategory createDutyCategory(DutyCategory dutyCategory);

    SubDuty createSubDuty(SubDuty subDuty, Integer category);

    List<Customer> seeAllCustomer();

    List<Expert> seeAllExpert();

    List<DutyCategory> seeAllDutyCategories();
    List<SubDuty> seeAllSubDuty();

    void addExpertInSubDuty(Integer expertId,Integer subDutyId);

    void confirmExpert(Integer expertId);
    void removeExpertFromSubDuty(Integer expertId,Integer subDutyId);


    void removeDutyCategory(Integer dutyCategoryId);

    void removeSubDuty(Integer subDutyId);

    void removeCustomer(Integer customerId);

    void removeExpert(Integer expertId);

    void updateSubDuty(Integer subDutyId,String newTitle,String newDescription,Double newBasePrice);

    List<SubDuty> seeSubDutyByCategory(Integer category);


    void updateDutyCategory(Integer dutyCategoryId,String newTitle);
    List<User> searchAdminByUser(String name, String lastName, String email, String expertise, Double minRate, Double maxRate);

    List<CustomerOrder> searchOrders(LocalDate startDate, LocalDate endDate, OrderStatus orderStatus,
                                     String category, String subDuty);
    List<WorkSuggestion> searchWorkSuggestionByName(String firstName, String lastName);



}
