package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;

import java.util.List;

public interface AdminService   {

    DutyCategory createDutyCategory(DutyCategory dutyCategory);

    SubDuty createSubDuty(SubDuty subDuty, Integer category);

    List<Customer> seeAllCustomer();

    List<Expert> seeAllExpert();

    List<DutyCategory> seeAllDutyCategories();
    List<SubDuty> seeAllSubDuty();

    Expert addExpertInSubDuty(Integer expertId,Integer subDutyId);

    Expert confirmExpert(Integer expertId);
    Expert removeExpertFromSubDuty(Integer expertId,Integer subDutyId);

    boolean validateExpertOneDutyCategory(Expert expert,Integer newDutyCategory);

    void removeDutyCategory(Integer dutyCategoryId);

    void removeSubDuty(Integer subDutyId);

    void removeCustomer(Integer customerId);

    void removeExpert(Integer expertId);

    SubDuty updateSubDuty(Integer subDutyId,String newTitle,String newDescription,Double newBasePrice);

    List<SubDuty> seeSubDutyByCategory(Integer category);


    DutyCategory updateDutyCategory(Integer dutyCategoryId,String newTitle);

}
