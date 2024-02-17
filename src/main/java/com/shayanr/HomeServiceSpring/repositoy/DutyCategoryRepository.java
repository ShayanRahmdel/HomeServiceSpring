package com.shayanr.HomeServiceSpring.repositoy;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DutyCategoryRepository extends JpaRepository<DutyCategory,Integer> {
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DutyCategory d WHERE d.title = :title")
    Boolean existsByTitle(@Param("title") String title);

    @Query("update DutyCategory d set d.title = :title where d.id= :id")
    DutyCategory updateDutyCategoriesById(@Param("title") String string,@Param("id") Integer id);
}
