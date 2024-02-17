package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;


public interface WorkSuggestionService {

    WorkSuggestion save(WorkSuggestion workSuggestion);


    WorkSuggestion findById(Integer workSuggestionId);

    void deleteById(Integer workSuggestionId);

    void deleteAll();





}
