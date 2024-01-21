package com.shayanr.HomeServiceSpring.service;



import com.shayanr.HomeServiceSpring.entity.business.SubDuty;

import java.util.List;

public interface SubDutyService {

    List<SubDuty> seeSubDutyByCategory(Integer category);


}
