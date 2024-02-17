package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.repositoy.WorkSuggestionRepository;
import com.shayanr.HomeServiceSpring.service.WorkSuggestionService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class WorkSuggestionServiceImpl implements WorkSuggestionService {
    private final WorkSuggestionRepository workSuggestionRepository;

    @Override
    @Transactional
    public WorkSuggestion save(WorkSuggestion workSuggestion) {
        try {
            workSuggestionRepository.save(workSuggestion);
        }catch (NullPointerException | PersistenceException e){
            System.out.println("Error saving work suggestion");
        }
        return workSuggestion;
    }

    @Override
    public WorkSuggestion findById(Integer workSuggestionId) {
     return workSuggestionRepository.findById(workSuggestionId).orElseThrow(()-> new NotFoundException("Work suggestion not found"));
    }

    @Override
    @Transactional
    public void deleteById(Integer workSuggestionId) {
            findById(workSuggestionId);
            workSuggestionRepository.deleteById(workSuggestionId);
    }

    @Override
    public void deleteAll() {
        workSuggestionRepository.deleteAll();
    }


}
