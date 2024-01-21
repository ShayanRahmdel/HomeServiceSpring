package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;

import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.repositoy.AdminRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@RequiredArgsConstructor
public class AdminServiceImpl {

    private AdminRepository adminRepository;
    private DutyCategoryServiceImpl dutyCategoryServiceImpl;
    private SubDutyServiceImpl subDutyServiceImpl;
    private CustomerServiceImpl customerServiceImpl;
    private ExpertServiceImpl expertServiceImpl;



    public DutyCategory createDutyCategory(DutyCategory dutyCategory) {
        try {

            Collection<DutyCategory> all = dutyCategoryServiceImpl.;
            for (DutyCategory dutyCategoris : all) {
                if (dutyCategoris.getTitle().equals(dutyCategory.getTitle())){
                    throw new PersistenceException("duplicate duty category title");
                }
            }
            if (dutyCategory.getTitle().equals("")){
                throw  new PersistenceException("Empty title");
            }
            dutyCategoryServiceImpl.);
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }

        return dutyCategory;
    }


    public SubDuty createSubDuty(SubDuty subDuty, Integer category) {
        try {
            System.out.println(seeAllDutyCategories());
            Collection<SubDuty> all = subDutyServiceImpl.findAll();
            for (SubDuty sub : all) {
                String description = sub.getDescription();
                if (subDuty.getDescription().equals(description)){
                    throw new PersistenceException("Duplicate description");
                }
            }
            DutyCategory dutyCategory = dutyCategoryServiceImpl.findById(category).orElseThrow(PersistenceException::new);
            subDuty.setDutyCategory(dutyCategory);
            if (subDuty.getDescription().equals("")){
                throw  new PersistenceException("Empty description");
            }
            subDutyServiceImpl.saveOrUpdate(subDuty);
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }

        return subDuty;
    }


    public List<Customer> seeAllCustomer() {
        Collection<Customer> allCustomer = customerServiceImpl.findAll();
        return allCustomer.stream().toList();
    }


    public List<Expert> seeAllExpert() {
        Collection<Expert> allExpert = expertServiceImpl.findAll();
        return allExpert.stream().toList();
    }


    public List<DutyCategory> seeAllDutyCategories() {

        Collection<DutyCategory> allDutyCategory = dutyCategoryServiceImpl.findAll();
        if (allDutyCategory.size() == 0) {
            System.out.println("No duty category");
        }
        return allDutyCategory.stream().toList();
    }


    public List<SubDuty> seeAllSubDuty() {
        Collection<SubDuty> allSubDuty = subDutyServiceImpl.findAll();
        if (allSubDuty.size() == 0) {
            System.out.println("No Subduty");
        }
        return allSubDuty.stream().toList();
    }


    public void addExpertInSubDuty(Integer expertId, Integer subDutyId) {
        List<Expert> experts1 = seeAllExpert();
        System.out.println(experts1);
        List<SubDuty> subDuties = seeAllSubDuty();
        System.out.println(subDuties);

        Expert expert = expertServiceImpl.findById(expertId).orElse(null);
        SubDuty subDuty = subDutyServiceImpl.findById(subDutyId).orElse(null);

        if (expert != null && subDuty != null && expert.getConfirmation().equals(Confirmation.Accepted) && validateExpertOneDutyCategory(expert,subDutyId)) {

            expert.getSubDuties().add(subDuty);
            subDuty.getExperts().add(expert);

            expertServiceImpl.saveOrUpdate(expert);
            subDutyServiceImpl.saveOrUpdate(subDuty);
        } else {
            System.out.println("Cannot add this Expert to subDuty.");
        }
    }


    public void confirmExpert(Integer expertId) {
        try {
            Expert expert = expertServiceImpl.findById(expertId).orElseThrow(()->new IllegalArgumentException("Cannot find id"));
            assert expert != null;
            expert.setConfirmation(Confirmation.Accepted);
            expertServiceImpl.saveOrUpdate(expert);
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }


    public void removeExpertFromSubDuty(Integer expertId, Integer subDutyId) {
        Optional<Expert> optionalExpert = expertServiceImpl.findById(expertId);
        Optional<SubDuty> optionalSubDuty = subDutyServiceImpl.findById(subDutyId);

        if (optionalExpert.isPresent() && optionalSubDuty.isPresent()) {
            Expert expert = optionalExpert.get();
            SubDuty subDuty = optionalSubDuty.get();

            expert.getSubDuties().remove(subDuty);
            subDuty.getExperts().remove(expert);

            expertServiceImpl.saveOrUpdate(expert);
            subDutyServiceImpl.saveOrUpdate(subDuty);
        }

    }


    public boolean validateExpertOneDutyCategory(Expert expert,Integer newDutyCategory) {
        Set<SubDuty> subDuties = expert.getSubDuties();
        SubDuty subDuty = subDutyServiceImpl.findById(newDutyCategory).orElse(null);
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


    public void removeDutyCategory(Integer dutyCategoryId) {
        List<DutyCategory> dutyCategories = seeAllDutyCategories();
        System.out.println(dutyCategories);
        try {
            dutyCategoryServiceImpl.deleteById(dutyCategoryId);
        }catch (IllegalArgumentException e){
            System.out.println("Unable to delete");
        }

    }


    public void removeSubDuty(Integer subDutyId) {
        List<SubDuty> subDuties = seeAllSubDuty();
        System.out.println(subDuties);
        try {
            subDutyServiceImpl.deleteById(subDutyId);
        }catch (IllegalArgumentException e){
            System.out.println("Unable to delete");
        }

    }


    public void removeCustomer(Integer customerId) {
        List<Customer> customers = seeAllCustomer();
        System.out.println(customers);
        try {
            customerServiceImpl.deleteById(customerId);
        }catch (IllegalArgumentException e){
            System.out.println("Unable to delete");
        }

    }


    public void removeExpert(Integer expertId) {
        List<Expert> experts = seeAllExpert();
        System.out.println(experts);
        try {
            expertServiceImpl.deleteById(expertId);
        }catch (IllegalArgumentException e){
            System.out.println("Unable to delete");
        }

    }


    public void updateSubDuty(Integer subDutyId, String newDescription, Double newBasePrice) {
        try{
            SubDuty subDuty = subDutyServiceImpl.findById(subDutyId).orElse(null);
            assert subDuty != null;
            if (newDescription.equals("")|| newBasePrice<=0){
                throw new IllegalArgumentException();
            }
            subDuty.setDescription(newDescription);
            subDuty.setBasePrice(newBasePrice);
            subDutyServiceImpl.saveOrUpdate(subDuty);
        }catch (IllegalArgumentException e){
            System.out.println("Error updating");
        }


    }


    public List<SubDuty> seeSubDutyByCategory(Integer category) {

        List<SubDuty> subDuties = subDutyServiceImpl.seeSubDutyByCategory(category);
        if (subDuties.size() == 0) {
            System.out.println("no SubDuty found for category ");
        }
        return subDuties;
    }



    public void deleteDutyCategory(Integer dutyCategoryId) {
        try {
            dutyCategoryServiceImpl.deleteById(dutyCategoryId);
        }catch (IllegalArgumentException e){
            System.out.println("Error: check dutyCategory id");
        }

    }


    public void deleteSubDuty(Integer subDutyId) {
        try {
            subDutyServiceImpl.deleteById(subDutyId);
        }catch (IllegalArgumentException e){
            System.out.println("Error: check SubDuty id");
        }

    }


    public void updateDutyCategory(Integer dutyCategoryId,String newTitle) {
        try {
            DutyCategory dutyCategory = dutyCategoryServiceImpl.findById(dutyCategoryId).orElse(null);
            assert dutyCategory != null;
            dutyCategory.setTitle(newTitle);
            if (newTitle.equals("")){
                throw new IllegalArgumentException();
            }
            dutyCategoryServiceImpl.saveOrUpdate(dutyCategory);
        }catch (IllegalArgumentException e){
            System.out.println("Error: check dutyCategory ");
        }
    }
}
