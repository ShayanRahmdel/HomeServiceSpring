package com.shayanr.HomeServiceSpring.service.impl;



import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.repositoy.SubDutyRepository;
import com.shayanr.HomeServiceSpring.service.SubDutyService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SubDutyServiceImpl implements SubDutyService {

    private final SubDutyRepository subDutyRepository;


    @Override
    @Transactional
    public SubDuty save(SubDuty subDuty) {
        try {
            subDutyRepository.save(subDuty);
        }catch (NullPointerException | PersistenceException e){
            System.out.println("Error saving subDuty");
        }
        return subDuty;
    }

    @Override
    @Transactional
    public void deleteById(Integer subDutyId) {
        if (subDutyId==null){
            throw new NullPointerException("cant find subduty");
        }
        subDutyRepository.deleteById(subDutyId);
    }

    @Override
    public SubDuty findById(Integer subDutyId) {
        if (subDutyId==null){
            throw new NullPointerException("cant find subduty");
        }
        return subDutyRepository.findById(subDutyId).orElse(null);


    }

    @Override
    public List<SubDuty> findAll() {
       return subDutyRepository.findAll();
    }

    @Override
    public List<SubDuty> seeSubDutyByCategory(Integer category) {
        List<SubDuty> subDutyList = new ArrayList<>();
        Collection<SubDuty> allSubDuty = subDutyRepository.findAll();
        for (SubDuty subDuty : allSubDuty){
            if (Objects.equals(subDuty.getDutyCategory().getId(), category)){
                subDutyList.add(subDuty);
            }

        }
        return subDutyList;

    }
}
