package com.shayanr.HomeServiceSpring.repositoy;


import com.example.homeservicespringdata.entity.business.DutyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DutyCategoryRepository extends JpaRepository<DutyCategory,Integer> {
}
