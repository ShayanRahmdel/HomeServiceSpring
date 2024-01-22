package com.shayanr.HomeServiceSpring.service;



import com.shayanr.HomeServiceSpring.entity.business.SubDuty;

import java.util.List;

public interface SubDutyService {

    SubDuty save(SubDuty subDuty);

    void deleteById(Integer subDutyId);

    SubDuty findById(Integer subDutyId);

    List<SubDuty> findAll();

    List<SubDuty> seeSubDutyByCategory(Integer category);


}
