package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import java.util.List;


public interface DutyCategoryService {

    DutyCategory save(DutyCategory category);

    DutyCategory findById(Integer dutyCategoryId);
    void updateDutyCategoriesById(String title, Integer id);

    void deleteById(Integer dutyCategoryId);

    List<DutyCategory> findAll();


    Boolean existsByTitle(String title);

    void deleteAll();
}
