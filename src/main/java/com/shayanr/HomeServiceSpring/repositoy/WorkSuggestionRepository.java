package com.shayanr.HomeServiceSpring.repositoy;


import com.example.homeservicespringdata.entity.business.WorkSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkSuggestionRepository extends JpaRepository<WorkSuggestion,Integer> {
}
