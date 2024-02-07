package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;

import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.business.Wallet;
import com.shayanr.HomeServiceSpring.entity.enumration.Confirmation;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.exception.DuplicateException;
import com.shayanr.HomeServiceSpring.exception.IsEmptyFieldException;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.exception.ValidationException;
import com.shayanr.HomeServiceSpring.repositoy.AdminRepository;
import com.shayanr.HomeServiceSpring.repositoy.AdminRepositoryCustom;
import com.shayanr.HomeServiceSpring.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        List<DutyCategory> all = dutyCategoryService.findAll();

        if (dutyCategory.getTitle().isEmpty()) {
            throw new IsEmptyFieldException("empty title");
        }
        for (DutyCategory category : all) {
            if (category.getTitle().equals(dutyCategory.getTitle())) {
                throw new DuplicateException("Duplicate title");
            }
        }
        dutyCategoryService.save(dutyCategory);
        return dutyCategory;

    }

    @Override
    @Transactional
    public SubDuty createSubDuty(SubDuty subDuty, Integer category) {
        Collection<SubDuty> all = subDutyService.findAll();

        DutyCategory dutyCategory = dutyCategoryService.findById(category).orElse(null);
        for (SubDuty sub : all) {
            String title = sub.getTitle();
            if (subDuty.getTitle().equals(title)) {
                throw new DuplicateException("Duplicate description");
            }
        }

        if (dutyCategory == null) {
            throw new NotFoundException("category not found");
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
    public Expert addExpertInSubDuty(Integer expertId, Integer subDutyId) {

        Expert expert = expertService.findById(expertId).orElse(null);
        SubDuty subDuty = subDutyService.findById(subDutyId).orElse(null);
        assert expert != null;
        if (!expert.getConfirmation().equals(Confirmation.ACCEPTED) ||
                !validateExpertOneDutyCategory(expert, subDutyId)) {
            throw new ValidationException("check validtoon again");
        }
        expert.getSubDuties().add(subDuty);
        assert subDuty != null;
        subDuty.getExperts().add(expert);

        expertService.save(expert);
        subDutyService.save(subDuty);
        return expert;
    }

    @Override
    @Transactional
    public Expert confirmExpert(Integer expertId) {

        Expert expert = expertService.findById(expertId).orElse(null);
        assert expert != null;
        expert.setConfirmation(Confirmation.ACCEPTED);
        Wallet wallet = new Wallet();
        wallet.setAmount(0.0);
        expert.setWallet(wallet);
        expertService.save(expert);
        return expert;

    }

    @Override
    @Transactional
    public Expert removeExpertFromSubDuty(Integer expertId, Integer subDutyId) {

        Expert expert = expertService.findById(expertId).orElse(null);
        SubDuty subDuty = subDutyService.findById(subDutyId).orElse(null);
        if (expert == null || subDuty == null) {
            throw new NullPointerException();
        }

        expert.getSubDuties().remove(subDuty);
        subDuty.getExperts().remove(expert);

        expertService.save(expert);
        subDutyService.save(subDuty);

        return expert;

    }

    @Override
    public boolean validateExpertOneDutyCategory(Expert expert, Integer newDutyCategory) {
        Set<SubDuty> subDuties = expert.getSubDuties();
        SubDuty subDuty = subDutyService.findById(newDutyCategory).orElse(null);
        assert subDuty != null;
        Integer id = subDuty.getDutyCategory().getId();
        for (SubDuty expertsubDuty : subDuties) {
            Integer expertid = expertsubDuty.getDutyCategory().getId();
            if (!Objects.equals(expertid, id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void removeDutyCategory(Integer dutyCategoryId) {
        DutyCategory dutyCategory = dutyCategoryService.findById(dutyCategoryId).orElse(null);
        dutyCategoryService.deleteById(dutyCategoryId);

    }

    @Override
    @Transactional
    public void removeSubDuty(Integer subDutyId) {
        SubDuty subDuty = subDutyService.findById(subDutyId).orElse(null);
        subDutyService.deleteById(subDutyId);

    }

    @Override
    @Transactional
    public void removeCustomer(Integer customerId) {
        Customer customer = customerService.findById(customerId).orElse(null);
        customerService.deleteById(customerId);

    }

    @Override
    @Transactional
    public void removeExpert(Integer expertId) {
        Expert expert = expertService.findById(expertId).orElse(null);
        expertService.deleteById(expertId);

    }

    @Override
    @Transactional
    public SubDuty updateSubDuty(Integer subDutyId, String newTitle, String newDescription, Double newBasePrice) {
        List<SubDuty> subDuties = subDutyService.findAll();


        for (SubDuty subDuty : subDuties) {
            if (newTitle.equals(subDuty.getTitle())) {
                throw new DuplicateException("duplicate title");
            }
        }
        SubDuty subDuty = subDutyService.findById(subDutyId).orElse(null);
        assert subDuty != null;
        if (newDescription.isEmpty() || newBasePrice <= 0 || newTitle.isEmpty()) {
            throw new ValidationException("check validation again");
        }
        subDuty.setTitle(newTitle);
        subDuty.setDescription(newDescription);
        subDuty.setBasePrice(newBasePrice);
        subDutyService.save(subDuty);
        return subDuty;
    }

    @Override
    public List<SubDuty> seeSubDutyByCategory(Integer category) {
        List<SubDuty> subDuties = subDutyService.findAll();
        List<SubDuty> subDutyList = new ArrayList<>();
        for (SubDuty subDuty : subDuties) {
            if (Objects.equals(subDuty.getDutyCategory().getId(), category)) {
                subDutyList.add(subDuty);
            }
        }

        return subDutyList;
    }


    @Override
    @Transactional
    public DutyCategory updateDutyCategory(Integer dutyCategoryId, String newTitle) {
        DutyCategory dutyCategory = dutyCategoryService.findById(dutyCategoryId).orElse(null);
        List<DutyCategory> dutyCategories = dutyCategoryService.findAll();
        for (DutyCategory dutyCategory1 : dutyCategories) {
            if (newTitle.isEmpty() || dutyCategory1.getTitle().equals(newTitle)) {
                throw new ValidationException("check again");
            }
        }
        assert dutyCategory != null;
        dutyCategory.setTitle(newTitle);
        return dutyCategoryService.save(dutyCategory);

    }

    @Override
    public List<Expert> searchAdminByExpert(String name, String lastName, String email, String expertise, Double minRate, Double maxRate) {
        return adminRepository.searchAdminByExpert(name, lastName, email,expertise,minRate,maxRate);
    }

    @Override
    public List<Customer> searchAdminByCustomer(String name, String lastName, String email) {
        return adminRepository.searchAdminByCustomer(name, lastName, email);
    }
}
