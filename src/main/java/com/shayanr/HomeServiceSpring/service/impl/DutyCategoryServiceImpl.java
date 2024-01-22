package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.repositoy.DutyCategoryRepository;
import com.shayanr.HomeServiceSpring.service.DutyCategoryService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public DutyCategory findById(Integer dutyCategoryId) {
        if (dutyCategoryId == null) {
            throw new NullPointerException("dutyCategoryId is null");
        }
        return dutyCategoryRepository.findById(dutyCategoryId).orElse(null);

    }

    @Override
    @Transactional
    public void deleteById(Integer dutyCategoryId) {
        if (dutyCategoryId == null){
            throw new NullPointerException("dutyCategoryId is null");
        }
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
        for (DutyCategory category : all) {
            if (category.getTitle().equals(newTitle) || newTitle.isEmpty()){
                throw new PersistenceException("Duplicate duty category or title empty");
            }
        }
        if (dutyCategoryId == null){
            throw new NullPointerException("id is Null");
        }
        DutyCategory dutyCategory = dutyCategoryRepository.findById(dutyCategoryId).orElse(null);
        assert dutyCategory != null;
        dutyCategory.setTitle(newTitle);
        dutyCategoryRepository.save(dutyCategory);

    }
}
