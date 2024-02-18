package com.shayanr.HomeServiceSpring.repositoy;


import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DutyCategoryRepository extends JpaRepository<DutyCategory,Integer> {
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DutyCategory d WHERE d.title = :title")
    Boolean existsByTitle(@Param("title") String title);

    @Modifying
    @Query("UPDATE DutyCategory d SET d.title = :title WHERE d.id = :id")
    void updateDutyCategoriesById(@Param("title")String title ,@Param("id") Integer id);
}
