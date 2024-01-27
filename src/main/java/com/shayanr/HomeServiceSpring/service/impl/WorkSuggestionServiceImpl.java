package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.repositoy.WorkSuggestionRepository;
import com.shayanr.HomeServiceSpring.service.WorkSuggestionService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        WorkSuggestion workSuggestion = workSuggestionRepository.findById(workSuggestionId).orElse(null);
        if (workSuggestion == null) {
            throw new NullPointerException("workSuggestion is null");
        }
        return workSuggestion;
    }

    @Override
    @Transactional
    public void deleteById(Integer workSuggestionId) {
            if (workSuggestionId==null){
                throw new NullPointerException("cant find workSuggestion");
            }
            workSuggestionRepository.deleteById(workSuggestionId);
    }

    @Override
    public void deleteAll() {
        workSuggestionRepository.deleteAll();
    }


}
