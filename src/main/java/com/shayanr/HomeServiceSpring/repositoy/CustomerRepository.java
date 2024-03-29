package com.shayanr.HomeServiceSpring.repositoy;


import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer>,CustomerRepositoryCustom {
    @Query("SELECT w FROM WorkSuggestion w JOIN w.customerOrder o WHERE o.customer.id = :customerId order by w.suggestedPrice ASC ")
    List<WorkSuggestion> seeSuggestionsByPrice(@Param("customerId")Integer customerId);


    @Query("SELECT w FROM WorkSuggestion w JOIN w.customerOrder o JOIN w.expert e WHERE o.customer.id = :customerId ORDER BY e.overallScore DESC")
    List<WorkSuggestion> seeSuggestionsByExpertScore(@Param("customerId") Integer customerId);
}
