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

    void addExpertInSubDuty(Integer expertId,Integer subDutyId);

    void confirmExpert(Integer expertId);
    void removeExpertFromSubDuty(Integer expertId,Integer subDutyId);

    boolean validateExpertOneDutyCategory(Expert expert,Integer newDutyCategory);

    void removeDutyCategory(Integer dutyCategoryId);

    void removeSubDuty(Integer subDutyId);

    void removeCustomer(Integer customerId);

    void removeExpert(Integer expertId);

    void updateSubDuty(Integer subDutyId,String newDescription,Double newBasePrice);

    List<SubDuty> seeSubDutyByCategory(Integer category);
    void deleteDutyCategory(Integer dutyCategoryId);

    void deleteSubDuty(Integer subDutyId);

    void updateDutyCategory(Integer dutyCategoryId,String newTitle);

}
