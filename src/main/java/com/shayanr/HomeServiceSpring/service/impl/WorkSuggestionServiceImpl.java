package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.repositoy.WorkSuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkSuggestionServiceImpl {
    private WorkSuggestionRepository workSuggestionRepository;
}
