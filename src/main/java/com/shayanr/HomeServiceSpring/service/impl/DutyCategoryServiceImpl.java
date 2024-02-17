package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.repositoy.DutyCategoryRepository;
import com.shayanr.HomeServiceSpring.service.DutyCategoryService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        return dutyCategoryRepository.findById(dutyCategoryId).orElseThrow(()-> new NotFoundException("Not found duty category"));

    }

    @Override
    public DutyCategory updateDutyCategoriesById(String title, Integer id) {
        return dutyCategoryRepository.updateDutyCategoriesById(title, id);
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
    public Boolean existsByTitle(String title) {
        return dutyCategoryRepository.existsByTitle(title);
    }

    @Override
    @Transactional
    public void deleteAll() {
        dutyCategoryRepository.deleteAll();
    }
}
