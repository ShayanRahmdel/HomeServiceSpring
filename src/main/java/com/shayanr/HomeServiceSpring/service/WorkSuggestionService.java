package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;

import java.util.List;
import java.util.Optional;

public interface WorkSuggestionService {

    WorkSuggestion save(WorkSuggestion workSuggestion);


    Optional<WorkSuggestion> findById(Integer workSuggestionId);

    void deleteById(Integer workSuggestionId);





}
