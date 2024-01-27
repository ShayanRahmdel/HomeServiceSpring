package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;

import java.util.List;

public interface DutyCategoryService {

    DutyCategory save(DutyCategory category);

    DutyCategory findById(Integer dutyCategoryId);

    void deleteById(Integer dutyCategoryId);

    List<DutyCategory> findAll();
    void updateDutyCategory(Integer dutyCategoryId,String newTitle);

    void deleteAll();
}
