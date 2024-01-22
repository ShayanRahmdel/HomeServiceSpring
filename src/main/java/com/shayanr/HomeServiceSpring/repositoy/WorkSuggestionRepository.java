package com.shayanr.HomeServiceSpring.repositoy;


import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkSuggestionRepository extends JpaRepository<WorkSuggestion,Integer> {

    @Query("SELECT w FROM WorkSuggestion w JOIN w.order o WHERE o.customer.id = :customerId")
    List<WorkSuggestion> seeWorkSuggestions(@Param("customerId")Integer customerId);
}
