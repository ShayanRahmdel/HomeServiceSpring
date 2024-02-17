package com.shayanr.HomeServiceSpring.repositoy;


import com.shayanr.HomeServiceSpring.entity.users.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpertRepository extends JpaRepository<Expert,Integer> {
    @Query("SELECT s.dutyCategory.id FROM Expert e JOIN e.subDuties s WHERE e.id = :id")
    Integer expertCategory(@Param("id") Integer id);
}
