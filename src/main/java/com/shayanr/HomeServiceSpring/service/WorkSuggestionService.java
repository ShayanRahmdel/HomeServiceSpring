package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;

import java.util.List;

public interface WorkSuggestionService {

    WorkSuggestion save(WorkSuggestion workSuggestion);

    void deleteById(Integer workSuggestionId);


    List<WorkSuggestion> seeSuggestions(Integer customerId);


}
