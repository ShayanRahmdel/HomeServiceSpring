package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.*;
import com.shayanr.HomeServiceSpring.entity.enumration.Confirmation;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.entity.users.User;
import com.shayanr.HomeServiceSpring.exception.DuplicateException;
import com.shayanr.HomeServiceSpring.exception.IsEmptyFieldException;
import com.shayanr.HomeServiceSpring.exception.ValidationException;
import com.shayanr.HomeServiceSpring.repositoy.AdminRepository;
import com.shayanr.HomeServiceSpring.repositoy.AdminRepositoryCustom;
import com.shayanr.HomeServiceSpring.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService, AdminRepositoryCustom {

    private final AdminRepository adminRepository;
    private final DutyCategoryService dutyCategoryService;
    private final SubDutyService subDutyService;
    private final CustomerService customerService;
    private final ExpertService expertService;


    @Override
    @Transactional
    public DutyCategory createDutyCategory(DutyCategory dutyCategory) {

        if (dutyCategory.getTitle().isEmpty()) {
            throw new IsEmptyFieldException("empty title");
        }
        if (dutyCategoryService.existsByTitle(dutyCategory.getTitle())) {
            throw new DuplicateException("Duplicate title");
        }

        dutyCategoryService.save(dutyCategory);
        return dutyCategory;

    }

    @Override
    @Transactional
    public SubDuty createSubDuty(SubDuty subDuty, Integer category) {


        DutyCategory dutyCategory = dutyCategoryService.findById(category);

        if (subDutyService.existsByTitle(subDuty.getTitle())) {
            throw new DuplicateException("Duplicate description");
        }

        subDuty.setDutyCategory(dutyCategory);
        if (subDuty.getDescription().equals("")) {
            throw new IsEmptyFieldException("Empty description");
        }
        subDutyService.save(subDuty);
        return subDuty;

    }

    @Override
    public List<Customer> seeAllCustomer() {
        return customerService.findAll();
    }

    @Override
    public List<Expert> seeAllExpert() {
        return expertService.findAll();
    }

    @Override
    public List<DutyCategory> seeAllDutyCategories() {
        return dutyCategoryService.findAll();
    }

    @Override
    public List<SubDuty> seeAllSubDuty() {
        return subDutyService.findAll();
    }

    @Override
    @Transactional
    public void addExpertInSubDuty(Integer expertId, Integer subDutyId) {

        Expert expert = expertService.findById(expertId);
        SubDuty subDuty = subDutyService.findById(subDutyId);
        if (expertService.expertCategory(expertId) != null && !Objects.equals(expertService.expertCategory(expertId), subDuty.getDutyCategory().getId())) {
            throw new ValidationException("Expert category is diifferent ");
        }

        if (!expert.getConfirmation().equals(Confirmation.ACCEPTED)) {
            throw new ValidationException("expert Not confirmed");
        }
        expert.getSubDuties().add(subDuty);
        subDuty.getExperts().add(expert);

        expertService.save(expert);
        subDutyService.save(subDuty);

    }

    @Override
    @Transactional
    public void confirmExpert(Integer expertId) {

        Expert expert = expertService.findById(expertId);
        expert.setConfirmation(Confirmation.ACCEPTED);
        Wallet wallet = new Wallet();
        wallet.setAmount(0.0);
        expert.setWallet(wallet);
        expertService.save(expert);


    }

    @Override
    @Transactional
    public void removeExpertFromSubDuty(Integer expertId, Integer subDutyId) {

        Expert expert = expertService.findById(expertId);
        SubDuty subDuty = subDutyService.findById(subDutyId);

        expert.getSubDuties().remove(subDuty);
        subDuty.getExperts().remove(expert);

        expertService.save(expert);
        subDutyService.save(subDuty);

    }


    @Override
    @Transactional
    public void removeDutyCategory(Integer dutyCategoryId) {
        dutyCategoryService.findById(dutyCategoryId);
        dutyCategoryService.deleteById(dutyCategoryId);

    }

    @Override
    @Transactional
    public void removeSubDuty(Integer subDutyId) {
        subDutyService.findById(subDutyId);
        subDutyService.deleteById(subDutyId);

    }

    @Override
    @Transactional
    public void removeCustomer(Integer customerId) {
        customerService.findById(customerId);
        customerService.deleteById(customerId);

    }

    @Override
    @Transactional
    public void removeExpert(Integer expertId) {
        expertService.findById(expertId);
        expertService.deleteById(expertId);

    }

    @Override
    @Transactional
    public void updateSubDuty(Integer subDutyId, String newTitle, String newDescription, Double newBasePrice) {
        if (newTitle == null) {
            throw new ValidationException("newTitle cannot be null");
        }
        if (newDescription==null){
            throw new ValidationException("newDescription cannot be null");
        }
        if (newBasePrice == null){
            throw new ValidationException("newBasePrice cannot be null");
        }
        if (subDutyService.existsByTitle(newTitle) || newTitle.isEmpty()) {
            throw new DuplicateException("duplicate title");
        }
        if (newDescription.isEmpty()|| newBasePrice <= 0) {
            throw new ValidationException("check validation again");
        }
         subDutyService.updateSubDutyById(subDutyId, newTitle, newDescription, newBasePrice);
    }

    @Override
    public List<SubDuty> seeSubDutyByCategory(Integer category) {
        return subDutyService.seeSubDutyByCategory(category);
    }


    @Override
    @Transactional
    public void updateDutyCategory(Integer dutyCategoryId, String newTitle) {

        if (newTitle == null) {
            throw new ValidationException("title cannot be null");
        }
        if (newTitle.isEmpty()) {
            throw new ValidationException("check again");
        }
         dutyCategoryService.updateDutyCategoriesById(newTitle, dutyCategoryId);
    }

    @Override
    public List<User> searchAdminByUser(String firstName, String lastName, String email,
                                        String expertise, Double minRate, Double maxRate, LocalTime registrationTime,
                                        LocalTime registerTo) {
        return adminRepository.searchAdminByUser(firstName, lastName, email, expertise, minRate, maxRate,
                registrationTime,registerTo);
    }

    @Override
    public List<WorkSuggestion> searchWorkSuggestionByName(String firstName, String lastName) {
        return adminRepository.searchWorkSuggestionByName(firstName, lastName);
    }

    @Override
    public List<Expert> searchExpertByCountSuggest(Integer desiredCount) {
        return adminRepository.searchExpertByCountSuggest(desiredCount);
    }

    @Override
    public List<Customer> searchCustomerByCountOrder(Integer desiredCount) {
        return adminRepository.searchCustomerByCountOrder(desiredCount);
    }

    @Override
    public List<CustomerOrder> seeOrdersByFullName(String firstName, String lastName) {
        return adminRepository.seeOrdersByFullName(firstName, lastName);
    }

    @Override
    public List<CustomerOrder> searchOrders(LocalDate startDate, LocalDate endDate, OrderStatus orderStatus, String category, String subDuty) {
        return adminRepository.searchOrders(startDate, endDate, orderStatus, category, subDuty);
    }
}
