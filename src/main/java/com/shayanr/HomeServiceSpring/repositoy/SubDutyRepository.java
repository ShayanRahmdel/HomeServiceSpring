package com.shayanr.HomeServiceSpring.repositoy;


import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubDutyRepository extends JpaRepository<SubDuty,Integer> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM SubDuty s WHERE s.title = :title")
    Boolean existsByTitle(@Param("title") String title);

    @Query("SELECT s from SubDuty s where s.dutyCategory.id= :id")
    List<SubDuty> seeSubDutyByCategory(@Param("id") Integer id);

    @Query("UPDATE SubDuty s set s.title = :title , s.description= :description, s.basePrice= :basePrice where s.id= :id")
    SubDuty updateSubDutyById(@Param("id") Integer id,@Param("title") String title
            ,@Param("description") String description,@Param("basePrice")Double basePrice);
}
