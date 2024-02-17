package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.repositoy.SubDutyRepository;
import com.shayanr.HomeServiceSpring.service.SubDutyService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
public class SubDutyServiceImpl implements SubDutyService {

    private final SubDutyRepository subDutyRepository;


    @Override
    @Transactional
    public SubDuty save(SubDuty subDuty) {
        try {
            subDutyRepository.save(subDuty);
        } catch (NullPointerException | PersistenceException e) {
            System.out.println("Error saving subDuty");
        }
        return subDuty;
    }

    @Override
    @Transactional
    public void deleteById(Integer subDutyId) {
        if (subDutyId == null) {
            throw new NotFoundException("cant find subduty");
        }
        subDutyRepository.deleteById(subDutyId);
    }

    @Override
    public SubDuty findById(Integer subDutyId) {
        return subDutyRepository.findById(subDutyId).orElseThrow(()-> new NotFoundException("cant find subduty"));
    }

    @Override
    public SubDuty updateSubDutyById(Integer id, String title, String description, Double basePrice) {
        return  subDutyRepository.updateSubDutyById(id, title, description, basePrice);
    }

    @Override
    public List<SubDuty> findAll() {
        return subDutyRepository.findAll();
    }

    @Override
    public List<SubDuty> seeSubDutyByCategory(Integer category) {
    return subDutyRepository.seeSubDutyByCategory(category);
    }

    @Override
    public Boolean existsByTitle(String title) {
        return subDutyRepository.existsByTitle(title);
    }

    @Override
    @Transactional
    public void deleteAll() {
        subDutyRepository.deleteAll();
    }

}
