package com.shayanr.HomeServiceSpring.service.impl;



import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.repositoy.SubDutyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
public class SubDutyServiceImpl {

    private SubDutyRepository subDutyRepository;



    public List<SubDuty> seeSubDutyByCategory(Integer category) {
        List<SubDuty> subDutyList = new ArrayList<>();
        Collection<SubDuty> allSubDuty = repository.findAll();
        for (SubDuty subDuty : allSubDuty){
            if (Objects.equals(subDuty.getDutyCategory().getId(), category)){
                subDutyList.add(subDuty);
            }

        }
        return subDutyList;
    }
}
