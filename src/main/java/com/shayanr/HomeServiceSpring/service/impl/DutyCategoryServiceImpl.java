package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.repositoy.DutyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DutyCategoryServiceImpl {

    private DutyCategoryRepository dutyCategoryRepository;


    public void updateDutyCategory(Integer dutyCategoryId ,String newTitle) {
        try {
            DutyCategory dutyCategory = repository.findById(dutyCategoryId).orElse(null);
            assert dutyCategory != null;
            dutyCategory.setTitle(newTitle);
            repository.saveOrUpdate(dutyCategory);
        }catch (NullPointerException e){
            System.out.println("Error: Wrong Id");
        }


    }
}
