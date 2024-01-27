package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;

import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.enumration.Confirmation;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.repositoy.AdminRepository;
import com.shayanr.HomeServiceSpring.service.*;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final DutyCategoryService dutyCategoryService;
    private final SubDutyService subDutyService;
    private final CustomerService customerService;
    private final ExpertService expertService;


    @Override
    @Transactional
    public DutyCategory createDutyCategory(DutyCategory dutyCategory) {
        List<DutyCategory> all = dutyCategoryService.findAll();
        try {
            if (dutyCategory.getTitle().isEmpty()) {
                throw new PersistenceException("empty title");
            }
            for (DutyCategory category : all) {
                if (category.getTitle().equals(dutyCategory.getTitle())) {
                    throw new PersistenceException("Duplicate title");
                }
            }
            dutyCategoryService.save(dutyCategory);
            return dutyCategory;
        } catch (PersistenceException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public SubDuty createSubDuty(SubDuty subDuty, Integer category) {
        Collection<SubDuty> all = subDutyService.findAll();
        try {
            DutyCategory dutyCategory = dutyCategoryService.findById(category);
            for (SubDuty sub : all) {
                String title = sub.getTitle();
                if (subDuty.getTitle().equals(title)) {
                    throw new PersistenceException("Duplicate description");
                }
            }

            if (dutyCategory == null) {
                throw new PersistenceException("category not found");
            }
            subDuty.setDutyCategory(dutyCategory);
            if (subDuty.getDescription().equals("")) {
                throw new PersistenceException("Empty description");
            }
            subDutyService.save(subDuty);
            return subDuty;
        } catch (PersistenceException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return null;
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

        Expert expert = expertService.findById(expertId);
        SubDuty subDuty = subDutyService.findById(subDutyId);
        if (expert == null || subDuty == null || !expert.getConfirmation().equals(Confirmation.ACCEPTED) ||
                !validateExpertOneDutyCategory(expert, subDutyId)) {
            throw new PersistenceException();
        }
        expert.getSubDuties().add(subDuty);
        subDuty.getExperts().add(expert);

        expertService.save(expert);
        subDutyService.save(subDuty);
        return expert;
    }

    @Override
    @Transactional
    public Expert confirmExpert(Integer expertId) {

        try {
            Expert expert = expertService.findById(expertId);
            expert.setConfirmation(Confirmation.ACCEPTED);
            expertService.save(expert);
            return expert;
        } catch (NullPointerException | PersistenceException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public Expert removeExpertFromSubDuty(Integer expertId, Integer subDutyId) {

        Expert expert = expertService.findById(expertId);
        SubDuty subDuty = subDutyService.findById(subDutyId);
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
        SubDuty subDuty = subDutyService.findById(newDutyCategory);
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
        try {
            dutyCategoryService.deleteById(dutyCategoryId);
        } catch (NullPointerException e) {
            System.out.println("Unable to delete");
        }
    }

    @Override
    @Transactional
    public void removeSubDuty(Integer subDutyId) {
        try {
            subDutyService.deleteById(subDutyId);
        } catch (NullPointerException e) {
            System.out.println("Unable to delete");
        }
    }

    @Override
    @Transactional
    public void removeCustomer(Integer customerId) {
        try {
            customerService.deleteById(customerId);
        } catch (NullPointerException e) {
            System.out.println("Unable to delete");
        }
    }

    @Override
    @Transactional
    public void removeExpert(Integer expertId) {
        try {
            expertService.deleteById(expertId);
        } catch (NullPointerException e) {
            System.out.println("Unable to delete");
        }
    }

    @Override
    @Transactional
    public SubDuty updateSubDuty(Integer subDutyId, String newTitle, String newDescription, Double newBasePrice) {
        List<SubDuty> subDuties = subDutyService.findAll();


        for (SubDuty subDuty : subDuties) {
            if (newTitle.equals(subDuty.getTitle())) {
                throw new PersistenceException("duplicate title");
            }
        }
        SubDuty subDuty = subDutyService.findById(subDutyId);
        assert subDuty != null;
        if (newDescription.isEmpty() || newBasePrice <= 0 || newTitle.isEmpty()) {
            throw new PersistenceException();
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
        DutyCategory dutyCategory = dutyCategoryService.findById(dutyCategoryId);
        List<DutyCategory> dutyCategories = dutyCategoryService.findAll();
        for (DutyCategory dutyCategory1 : dutyCategories ) {
            if (newTitle.isEmpty() || dutyCategory1.getTitle().equals(newTitle)) {
                throw new PersistenceException();
            }
        }
        dutyCategory.setTitle(newTitle);
        return dutyCategoryService.save(dutyCategory);

    }
}
