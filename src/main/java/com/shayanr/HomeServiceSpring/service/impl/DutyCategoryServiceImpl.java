package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.repositoy.DutyCategoryRepository;
import com.shayanr.HomeServiceSpring.service.DutyCategoryService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DutyCategoryServiceImpl implements DutyCategoryService {

    private final DutyCategoryRepository dutyCategoryRepository;


    @Override
    @Transactional
    public DutyCategory save(DutyCategory category) {
        try {
            dutyCategoryRepository.save(category);
        }catch (NullPointerException | PersistenceException e){
            System.out.println("Error saving duty category");
        }
        return category;
    }

    @Override
    public Optional<DutyCategory> findById(Integer dutyCategoryId) {
        Optional<DutyCategory> dutyCategory = dutyCategoryRepository.findById(dutyCategoryId);
        if (dutyCategory.isEmpty()) {
            throw new NotFoundException("dutyCategoryId is null");
        }
        return dutyCategory;

    }

    @Override
    @Transactional
    public void deleteById(Integer dutyCategoryId) {
        dutyCategoryRepository.deleteById(dutyCategoryId);
    }

    @Override
    public List<DutyCategory> findAll() {
        return dutyCategoryRepository.findAll();
    }

    @Override
    @Transactional
    public void updateDutyCategory(Integer dutyCategoryId, String newTitle) {
        List<DutyCategory> all = dutyCategoryRepository.findAll();
        try {
            dutyCategoryRepository.findById(dutyCategoryId).orElseThrow(()->new NullPointerException("cant find dutycategory"));
            for (DutyCategory category : all) {
                if (category.getTitle().equals(newTitle) || newTitle.isEmpty()){
                    throw new PersistenceException("Duplicate duty category or title empty");
                }
            }

            DutyCategory dutyCategory = dutyCategoryRepository.findById(dutyCategoryId).orElse(null);
            assert dutyCategory != null;
            dutyCategory.setTitle(newTitle);
            dutyCategoryRepository.save(dutyCategory);
        }catch (NullPointerException | PrivateAccessorException e){
            System.out.println(e.getMessage());
        }


    }

    @Override
    @Transactional
    public void deleteAll() {
        dutyCategoryRepository.deleteAll();
    }
}
